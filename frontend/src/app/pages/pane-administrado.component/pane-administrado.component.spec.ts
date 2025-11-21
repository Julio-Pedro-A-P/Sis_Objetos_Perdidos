import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaneAdministradoComponent } from './pane-administrado.component';

describe('PaneAdministradoComponent', () => {
  let component: PaneAdministradoComponent;
  let fixture: ComponentFixture<PaneAdministradoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaneAdministradoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaneAdministradoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
