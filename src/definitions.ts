export interface BulkContactPlugin {
  getPermissions(): Promise<PermissionStatus>;
  getContacts(): Promise<{ contacts: Contact[] }>;
  saveContact(contact: NewContact): Promise<void>;
}

export interface PermissionStatus {
  granted: boolean;
}

export interface PhoneNumber {
  label?: string;
  number?: string;
}

export interface EmailAddress {
  // TODO: make label an enum of android and ios label types to map them later + string for iOS
  label?: string;
  address?: string;
}

export interface Contact {
  contactId: string;
  displayName?: string;
  phoneNumbers: PhoneNumber[];
  emails: EmailAddress[];
  photoThumbnail?: string;
  organizationName?: string;
  organizationRole?: string;
  birthday?: string;
}

/**
 * New contact schema.
 *
 * @see https://developer.apple.com/documentation/contacts/cnmutablecontact
 * @see android-link...
 */
export interface NewContact {
  // Name information
  name: string;
  mobile: string;
}
