import Foundation

@objc public class BulkContact: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
