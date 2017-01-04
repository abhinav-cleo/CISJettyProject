/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { MenuoptionsSecurityComponentComponent } from './menuoptions-security-component.component';

describe('MenuoptionsSecurityComponentComponent', () => {
  let component: MenuoptionsSecurityComponentComponent;
  let fixture: ComponentFixture<MenuoptionsSecurityComponentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MenuoptionsSecurityComponentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MenuoptionsSecurityComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
