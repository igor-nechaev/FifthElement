package com.nech9ev.fifthelement

class TransactionManagerConfig(
    val transactionsUploadSize: Long = 2L,
    val postUrl: String = "http://172.20.10.5:8080/api/write/networkTraffic",
)
