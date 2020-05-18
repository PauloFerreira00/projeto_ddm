package br.com.farras.appzinho.models

class Result<T>(val success: T? = null, val failure: Error? = null)

class Error(val message: String?)