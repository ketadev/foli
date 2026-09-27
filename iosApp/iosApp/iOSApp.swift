import SwiftUI
import Shared

@main
struct iOSApp: App {
    init() {
        // Starts Foli's Koin graph once, before ContentView creates the
        // Compose UIViewController. See AppKoin.kt / AppKoin.ios.kt in
        // `shared` for the guarded, idempotent startup contract.
        AppKoin_iosKt.initializeIosDependencies()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}