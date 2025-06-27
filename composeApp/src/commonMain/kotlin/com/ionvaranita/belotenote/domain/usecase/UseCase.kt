package com.ionvaranita.belotenote.domain.usecase

interface UseCase <in A, out B>  {
    suspend fun execute(params: A): B
}