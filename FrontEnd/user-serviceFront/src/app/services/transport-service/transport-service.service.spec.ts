import { TestBed } from '@angular/core/testing';

import { TransportService } from '../transport-service/transport-service.service';

describe('TransportServiceService', () => {
  let service: TransportService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TransportService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
