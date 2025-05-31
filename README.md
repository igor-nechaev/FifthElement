# FifthElement SDK

**FifthElement** is an Android SDK for collecting, intercepting, and analyzing network traffic in Android applications. It's designed to help developers monitor user behavior across multiple gateways such as HTTP, WebView, media players, and download managers.

##  Key Features

- **Traffic Interception**
    - Intercepts network requests via custom OkHttp Interceptor (`FifthElementInterceptor`)
    - Tracks file downloads with `DownloadManagerBroadcastReceiver`
    - Monitors video streaming using ExoPlayer events

- **WebView Monitoring**
    - Wraps Android WebView to capture page loads and interactions (`FifthElementWebView`)

- **Visitor Pattern for Analytics**
    - Supports custom analytics by visiting transactions (e.g., `HttpRequestTransactionVisitor`, `DownloadManagerTransactionVisitor`)

- **Configuration**
    - Centralized config management via `TransactionManagerConfig` and `TransactionCollectorProvider`

## Modules

- `fifthelement`: core SDK module
- All network and media tracking logic is implemented here using gateways and visitors

##  Usage

1. Add the SDK to your project as a module
2. Initialize `TransactionManagerConfig` with your collector implementation
3. Plug interceptors into your network layer (e.g., OkHttp)
4. Use `FifthElementWebView` and track download or video events

## Server Integration

Pair with [fifth-element-server](https://github.com/SmartOven/fifth-element-server/tree/main) to store and analyze collected traffic data.
