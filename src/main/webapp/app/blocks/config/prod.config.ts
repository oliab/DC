import { enableProdMode } from '@angular/core';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';

export function ProdConfig(): void {
  // disable debug data on prod profile to improve performance
  if (!DEBUG_INFO_ENABLED) {
    enableProdMode();
  }
}
