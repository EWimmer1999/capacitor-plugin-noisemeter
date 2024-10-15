import Foundation

@objc public class NoiseMeter: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
