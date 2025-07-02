import { Component, ViewChild } from '@angular/core';
import { Observable } from 'rxjs';
import { TranslateService } from '@ngx-translate/core';
import { Router, Event } from '@angular/router';
import { MatSidenav, MatSidenavContent } from '@angular/material/sidenav';
import {
	Entities,




	} from './entities';

import { Globals } from 'src/app/core/services/globals';

@Component({
	selector: 'app-main-nav',
	templateUrl: './main-nav.component.html',
	styleUrls: ['./main-nav.component.scss', './main-nav-mixin.component.scss']
})
export class MainNavComponent {	
	@ViewChild("drawer", { static: false }) drawer: MatSidenav;
	@ViewChild("navContent", { static: false }) navContent: MatSidenavContent;
	
	appName: string = 'example';
	selectedLanguage: any;
	entityList = Entities;

	hasTaskAppPermission: boolean = false;
	hasAdminAppPermission: boolean = false;

	isSmallDevice$: Observable<boolean>;
	isMediumDevice$: Observable<boolean>;
	
	themes: any[] = ['default-theme', 'alt-theme'];
	username: any = "";

	
	
	constructor(
		public router: Router,
		public translate: TranslateService,
		public Global: Globals,

	) {

		this.isSmallDevice$ = Global.isSmallDevice$;
		this.isMediumDevice$ = Global.isMediumDevice$;
		
		this.selectedLanguage = localStorage.getItem('selectedLanguage');
		this.setPreferences();
		
	
	}
	
	
	isActive(url: any): boolean {
		return this.router.url.split('/').includes(url);
	}

	switchLanguage(language: string) {
		this.translate.use(language);
	    localStorage.setItem('selectedLanguage', language);
	    this.selectedLanguage = language;

	}
	
	setPreferences() {
		let theme = localStorage.getItem('theme');
		let language = localStorage.getItem('selectedLanguage');

		if (theme && theme != "undefined" && theme != "null") {
			this.changeTheme(theme, false);
		}
		else {
			this.changeTheme(this.themes[0], false);
		}
		if (language && language != "undefined" && language != "null") {
			this.selectedLanguage = language;
			this.translate.use(language);
		}
	}
	
	changeTheme(theme: any, updatePreference: boolean) {
		console.log("add css class");
		for (let i = 0; i < this.themes.length; i++) {
			if (document.body.className.match(this.themes[i])) {
				document.body.classList.remove(this.themes[i]);
			}
		}
		document.body.classList.add(theme);
    	localStorage.setItem('theme', theme);

	}
	
}