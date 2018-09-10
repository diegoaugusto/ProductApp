import { Component, OnInit } from '@angular/core';
import { Product } from '../product';

import { ProductService } from '../product.service';

import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

@Component({
    selector: 'app-products',
    templateUrl: './products.component.html',
    styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

    products: Product[];
    constructor(
        private route: ActivatedRoute,
        private productService: ProductService,
        private location: Location) { }

    ngOnInit() {
        this.getProducts();
    }

    getProducts(): void {
        this.productService.getProducts()
            .subscribe(products => this.products = products);
    }

    add(name: string, quantity: number): void {
        name = name.trim();
        if (!name) { return; }
        this.productService.addProduct({ name, quantity } as Product)
            .subscribe(product => {
                this.products.push(product);
            });
    }

    delete(product: Product): void {
        this.products = this.products.filter(h => h !== product);
        this.productService.deleteProduct(product).subscribe();
    }
}
