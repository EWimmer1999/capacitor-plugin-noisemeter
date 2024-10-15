// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorNoisemeter",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "CapacitorNoisemeter",
            targets: ["NoiseMeterPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "main")
    ],
    targets: [
        .target(
            name: "NoiseMeterPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/NoiseMeterPlugin"),
        .testTarget(
            name: "NoiseMeterPluginTests",
            dependencies: ["NoiseMeterPlugin"],
            path: "ios/Tests/NoiseMeterPluginTests")
    ]
)