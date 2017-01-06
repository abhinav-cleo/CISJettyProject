/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { AlertServiceService } from './alert-service.service';

describe('AlertServiceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AlertServiceService]
    });
  });

  it('should ...', inject([AlertServiceService], (service: AlertServiceService) => {
    expect(service).toBeTruthy();
  }));
});
