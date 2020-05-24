package br.com.farras.appzinho.helpers

class Result<T>(val success: T? = null, val failure: Error? = null)

class Error(val message: String?)