import { TestBed, async } from '@angular/core/testing';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { AppComponent } from './app.component';
import { ProductDetailComponent } from './product-detail/product-detail.component';
import { ProductsComponent } from './products/products.component';
import { MessagesComponent } from './messages/messages.component';

import { ProductService } from './product.service';
import { MessageService } from './message.service';

describe('AppComponent', () => {
  beforeEach(async(() => {
  TestBed.configureTestingModule({
    declarations: [
      AppComponent,
      ProductsComponent,
      ProductDetailComponent,
      MessagesComponent
    ],
    imports: [
      BrowserModule,
      FormsModule,
      AppRoutingModule,
      RouterTestingModule.withRoutes([]),
      HttpClientTestingModule
    ],
    providers: [
      ProductService,
      MessageService
    ]
  }).compileComponents();
}));

  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));

  it(`should have as title 'A Great App for Products'`, async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app.title).toEqual('A Great App for Products');
  }));

  it('should render title in a h1 tag', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('.navbar-brand').textContent).toContain('A Great App for Products');
  }));
});
