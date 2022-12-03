import { WebPlugin } from '@capacitor/core';

import type { BulkContactPlugin } from './definitions';

export class BulkContactWeb extends WebPlugin implements BulkContactPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
