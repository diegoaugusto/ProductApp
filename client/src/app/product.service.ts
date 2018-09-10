import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Product } from './product';
import { MessageService } from './message.service';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({ providedIn: 'root' })
export class ProductService {

  private productsUrl = 'http://localhost:8080/products';  // URL to web api

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

  /** GET products from the server */
  getProducts (): Observable<Product[]> {
      console.log(this.productsUrl);
    return this.http.get<Product[]>(this.productsUrl)
      .pipe(
        tap(products => this.log('fetched products')),
        catchError(this.handleError('getProducts', []))
      );
  }

  /** GET product by id. Return `undefined` when id not found */
//  getProductNo404<Data>(id: number): Observable<Product> {
//    const url = `${this.productsUrl}/${id}`;
//    return this.http.get<Product[]>(url)
//      .pipe(
//        map(products => products[0]), // returns a {0|1} element array
//        tap(h => {
//          const outcome = h ? `fetched` : `did not find`;
//          this.log(`${outcome} product id=${id}`);
//        }),
//        catchError(this.handleError<Product>(`getProduct id=${id}`))
//      );
//  }

  /** GET product by id. Will 404 if id not found */
  getProduct(id: number): Observable<Product> {
    const url = `${this.productsUrl}/${id}`;
    return this.http.get<Product>(url).pipe(
      tap(_ => this.log(`fetched product id=${id}`)),
      catchError(this.handleError<Product>(`getProduct id=${id}`))
    );
  }


  //////// Save methods //////////

  /** POST: add a new product to the server */
  addProduct (product: Product): Observable<Product> {
    return this.http.post<Product>(this.productsUrl, product, httpOptions).pipe(
      tap((prod: Product) => this.log(`added product w/ id=${prod.id}`)),
      catchError(this.handleError<Product>('addProduct'))
    );
  }

  /** DELETE: delete the product from the server */
  deleteProduct (product: Product | number): Observable<Product> {
    const id = typeof product === 'number' ? product : product.id;
    const url = `${this.productsUrl}/${id}`;

    return this.http.delete<Product>(url, httpOptions).pipe(
      tap(_ => this.log(`deleted product id=${id}`)),
      catchError(this.handleError<Product>('deleteProduct'))
    );
  }

  /** PUT: update the product on the server */
  updateProduct (product: Product): Observable<any> {
    const id = typeof product === 'number' ? product : product.id;
    const url = `${this.productsUrl}/${id}`;

    delete product.createdOn;
    delete product.modifiedOn;

    return this.http.patch(url, product, httpOptions).pipe(
      tap(_ => this.log(`updated product id=${id}`)),
      catchError(this.handleError<any>('updateProduct'))
    );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a ProductService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`ProductService: ${message}`);
  }
}
