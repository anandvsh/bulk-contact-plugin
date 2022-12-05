package com.uohmac.plugins.bulkcontact;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.provider.ContactsContract;

import com.getcapacitor.JSObject;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;

import org.json.JSONException;

import java.util.ArrayList;

@CapacitorPlugin(
name = "BulkContact",
    permissions = { @Permission(strings = { Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS }, alias = "bulkcontacts") }
)
public class BulkContactPlugin extends Plugin {

    private BulkContact implementation = new BulkContact();

    @PluginMethod
    public void getPermissions(PluginCall call){

      if (getPermissionState("bulkcontacts") != PermissionState.GRANTED) {
        requestPermissionForAlias("bulkcontacts", call, "contactPermsCallback");
      } else {
        JSObject result = new JSObject();
        result.put("granted", true);
        call.resolve(result);
      }

//       if (!hasRequiredPermissions()) {
//            requestPermissions(call);
//       } else {
//            JSObject result = new JSObject();
//            result.put("granted", true);
//            call.resolve(result);
//       }
    }

  @PermissionCallback
  public void contactPermsCallback(PluginCall call) {
    JSObject result = new JSObject();
    result.put("granted", getPermissionState("bulkcontacts") == PermissionState.GRANTED);
    call.resolve(result);
  }

//    @Override
//    protected void handleRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//            super.handleRequestPermissionsResult(requestCode, permissions, grantResults);
//
//            PluginCall savedCall = getSavedCall();
//            JSObject result = new JSObject();
//
//            if (!hasRequiredPermissions()) {
//                result.put("granted", false);
//                savedCall.resolve(result);
//            } else {
//                result.put("granted", true);
//                savedCall.resolve(result);
//            }
//    }

    @PluginMethod
    public void saveContact(PluginCall call) throws JSONException {

        String name = call.getString("name", "");
        String mobile = call.getString("mobile", "");
        this.saveMobile(new Person(name, mobile));
        call.resolve();

    }

    public void saveMobile(Person person) {

        try{

            ArrayList<ContentProviderOperation> contentProviderOperations
                    = new ArrayList<ContentProviderOperation>();

            contentProviderOperations.add(ContentProviderOperation.newInsert(
                            ContactsContract.RawContacts.CONTENT_URI)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                    .build());

            // Adding Name
            contentProviderOperations.add(ContentProviderOperation
                    .newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                            person.name)
                    .build());

            // Adding Number
            contentProviderOperations.add(ContentProviderOperation
                    .newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,
                            person.mobile)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                    .build());


            getActivity().getContentResolver().applyBatch(ContactsContract.AUTHORITY, contentProviderOperations);

        } catch (OperationApplicationException | RemoteException e) {
            e.printStackTrace();
        }

    }

}

class Person {
    // Random properties associated with the person
    // Person name
    String name;
    String mobile;
    // Constructor for class Person
    // for initializing objects
    Person(String name, String mobile) {
        // This keyword for efering to current object
        this.name = name;
        this.mobile = mobile;
    }
}
