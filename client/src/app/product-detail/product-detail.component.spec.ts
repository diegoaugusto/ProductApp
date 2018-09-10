import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormsModule } from '@angular/forms';

import { ProductDetailComponent } from './product-detail.component';
import { MessagesComponent } from '../messages/messages.component';

import { ProductService } from '../product.service';
import { MessageService } from '../message.service';

describe('ProductDetailComponent', () => {
  let component: ProductDetailComponent;
  let fixture: ComponentFixture<ProductDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
      ProductDetailComponent
    ],
    imports: [
      FormsModule,
      RouterTestingModule.withRoutes([]),
      HttpClientTestingModule
    ],
    providers: [
      ProductService,
      MessageService
    ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
