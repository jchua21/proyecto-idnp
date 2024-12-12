package com.jean.touraqp.core.utils

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.toObject

interface Identifiable {
    var id: String?
}

inline fun <reified T : Identifiable> DocumentSnapshot.toObjectWithId(): T {
    val data = this.toObject<T>() ?: throw Exception("Data Mismatch")
    data.id = this.id
    return data
}