// iosApp/ContentView.swift
import SwiftUI
import UserNotifications
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController { MainViewControllerKt.MainViewController() }
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    private let scheduler = ReminderSchedulerFactory().create()
    @State private var grantedText = "Unknown"
    var body: some View {
        ComposeView()
            .ignoresSafeArea(.keyboard)
            .onAppear {
                UNUserNotificationCenter.current().requestAuthorization(options: [.alert, .badge, .sound]) { g, _ in
                    DispatchQueue.main.async { grantedText = g ? "Granted" : "Denied" }
                }
                Task {
                    _ = try? await scheduler.requestPermission()
                    try? await scheduler.scheduleDaily(hour:19, minute: 30)
                }
            }
    }
}
