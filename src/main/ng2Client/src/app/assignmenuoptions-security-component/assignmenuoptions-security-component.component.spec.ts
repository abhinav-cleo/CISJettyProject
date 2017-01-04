/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { AssignmenuoptionsSecurityComponentComponent } from './assignmenuoptions-security-component.component';

describe('AssignmenuoptionsSecurityComponentComponent', () => {
  let component: AssignmenuoptionsSecurityComponentComponent;
  let fixture: ComponentFixture<AssignmenuoptionsSecurityComponentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssignmenuoptionsSecurityComponentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignmenuoptionsSecurityComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
