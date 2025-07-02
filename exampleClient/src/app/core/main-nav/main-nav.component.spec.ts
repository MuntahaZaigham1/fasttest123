
import { fakeAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { MatSidenavModule } from '@angular/material/sidenav';

import { MatExpansionModule } from '@angular/material/expansion';
import { MainNavComponent } from './main-nav.component';
import { TestingModule, EntryComponents } from 'src/testing/utils';

import { Router, Routes } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { DashboardExtendedComponent } from 'src/app/extended/core/dashboard';

describe('MainNavComponent', () => {
  let component: MainNavComponent;
  let fixture: ComponentFixture<MainNavComponent>;
  const routes: Routes = [
    { path: 'dashboard', component: DashboardExtendedComponent},
  ];
  beforeEach(fakeAsync(() => {
    TestBed.configureTestingModule({
    imports: [TestingModule,
             MatSidenavModule,
             MatExpansionModule,
             RouterTestingModule.withRoutes(routes),
            ],
      declarations: [MainNavComponent].concat(EntryComponents),

    })
    .compileComponents();

    fixture = TestBed.createComponent(MainNavComponent);
    component = fixture.componentInstance;
  }));

  it('should compile', () => {
    
    fixture.detectChanges();
    expect(component).toBeTruthy();
  });

  it('should switch the language', () => {
    spyOn(component.translate, 'use');
    spyOn(Storage.prototype, 'setItem').and.returnValue();

    let lang = 'en';
    component.switchLanguage(lang);

    expect(component.translate.use).toHaveBeenCalledWith(lang);
    expect(localStorage.setItem).toHaveBeenCalledWith('selectedLanguage',lang);
    expect(component.selectedLanguage).toEqual(lang);
  });

  it('should set user preference with the values fetched from localStorage', () => {
    let item = 'item';
    spyOn(component.translate, 'use');
    spyOn(Storage.prototype, 'getItem').and.returnValue(item);
    spyOn(component, 'changeTheme').and.returnValue();

    component.setPreferences();

    expect(component.changeTheme).toHaveBeenCalledWith(item, false);
    expect(component.selectedLanguage).toEqual(item);
    expect(component.translate.use).toHaveBeenCalledWith(item);
  });

  it('should set user preference with default values', () => {
    let themes = ['theme1'];
    let lang = 'en';

    component.selectedLanguage = lang;
    component.themes = themes;
    spyOn(component.translate, 'use');
    spyOn(Storage.prototype, 'getItem').and.returnValue("null");
    spyOn(component, 'changeTheme').and.returnValue();

    component.setPreferences();

    expect(component.changeTheme).toHaveBeenCalledWith(themes[0], false);
    expect(component.selectedLanguage).toEqual(lang);
    expect(component.translate.use).toHaveBeenCalledTimes(0);
  });
  
  it('should change theme without calling backend service', async () => {

    let themes = ['newTheme', 'appliedTheme'];
    component.themes = themes;
    document.body.classList.add(themes[1])

    component.changeTheme(themes[0], false);

    expect(document.body.classList.contains(themes[0])).toBeTruthy();
    expect(document.body.classList.contains(themes[1])).toBeFalsy();
  });


});
