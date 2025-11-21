import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeComponet } from './home.componet';

describe('HomeComponet', () => {
  let component: HomeComponet;
  let fixture: ComponentFixture<HomeComponet>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HomeComponet]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HomeComponet);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
