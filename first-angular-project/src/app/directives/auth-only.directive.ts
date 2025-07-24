import { Directive, ElementRef, Input } from '@angular/core';

@Directive({
  selector: '[appAuthOnly]',
})
export class AuthOnlyDirective {
  constructor(private elementRef: ElementRef) {}

  @Input() set appAuthOnly(condition: boolean) {
    if (condition) {
      this.elementRef.nativeElement.style.display = 'block';
    } else {
      this.elementRef.nativeElement.style.display = 'none';
    }
  }
}
