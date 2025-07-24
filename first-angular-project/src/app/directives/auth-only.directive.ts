import { Directive, effect, inject, input, TemplateRef, ViewContainerRef } from '@angular/core';

@Directive({
  selector: '[appAuthOnly]',
})
export class AuthOnlyDirective {
  private readonly _templateRef = inject(TemplateRef);
  private readonly _viewContainerRef = inject(ViewContainerRef);

  appAuthOnly = input(false);
  private _hasView = false;

  constructor() {
    effect(() => {
      this.handleView();
    });
  }

  private handleView(): void {
    if (this.appAuthOnly() && !this._hasView) {
      this._viewContainerRef.createEmbeddedView(this._templateRef); // create the view
      this._hasView = true;
    } else if (!this.appAuthOnly() && this._hasView) {
      this._viewContainerRef.clear(); // remove the view
      this._hasView = false;
    }
  }
}
