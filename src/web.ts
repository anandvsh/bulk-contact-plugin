import { WebPlugin } from '@capacitor/core';

import type {BulkContactPlugin,PermissionStatus, Contact, NewContact} from './definitions';

export class BulkContactWeb extends WebPlugin implements BulkContactPlugin {
  constructor() {
    super();
  }

  async getContacts(): Promise<{ contacts: Contact[] }> {
    throw this.unimplemented('getContacts - Not implemented on web.');
  }

  getPermissions(): Promise<PermissionStatus> {
    throw this.unimplemented('getPermissions - Not implemented on web.');
  }

  saveContact(_contact: NewContact): Promise<void> {
    throw this.unimplemented('saveContact - Not implemented on web.');
  }

}
