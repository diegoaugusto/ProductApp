import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormsModule } from '@angular/forms';

import { ProductsComponent } from './products.component';
import { MessagesComponent } from '../messages/messages.component';

import { ProductService } from '../product.service';
import { MessageService } from '../message.service';

describe('ProductsComponent', () => {
    let component: ProductsComponent;
    let fixture: ComponentFixture<ProductsComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [
                ProductsComponent
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
        fixture = TestBed.createComponent(ProductsComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it(`should have as page header, "Create new product"`, async(() => {
        fixture = TestBed.createComponent(ProductsComponent);
        const compiled = fixture.debugElement.nativeElement;
        expect(compiled.querySelector('.page-header').textContent).toContain('Create new product');
    }));
});
