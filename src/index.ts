import { registerPlugin } from '@capacitor/core';

import type { BulkContactPlugin } from './definitions';

const BulkContact = registerPlugin<BulkContactPlugin>('BulkContact', {
  web: () => import('./web').then(m => new m.BulkContactWeb()),
});

export * from './definitions';
export { BulkContact };
