import { create } from 'zustand'
import { persist } from 'zustand/middleware'
import type { User } from '@/types'
import { getToken, setToken, removeToken, setUser, removeUser } from '@/utils/storage'
import * as authApi from '@/api/auth'

interface AuthState {
  token: string | null
  user: User | null
  isAuthenticated: boolean

  // Actions
  login: (username: string, password: string) => Promise<void>
  logout: () => Promise<void>
  refreshToken: () => Promise<void>
  fetchCurrentUser: () => Promise<void>
  setAuthData: (token: string, user: User) => void
  clearAuth: () => void
}

export const useAuthStore = create<AuthState>()(
  persist(
    (set) => ({
      token: getToken(),
      user: null,
      isAuthenticated: !!getToken(),

      login: async (username: string, password: string) => {
        const result = await authApi.login({ username, password })
        setToken(result.token)
        setUser(result.user)
        set({
          token: result.token,
          user: result.user,
          isAuthenticated: true,
        })
      },

      logout: async () => {
        try {
          await authApi.logout()
        } finally {
          removeToken()
          removeUser()
          set({
            token: null,
            user: null,
            isAuthenticated: false,
          })
        }
      },

      refreshToken: async () => {
        const result = await authApi.refreshToken()
        setToken(result.token)
        set({ token: result.token })
      },

      fetchCurrentUser: async () => {
        const user = await authApi.getCurrentUser()
        setUser(user)
        set({ user })
      },

      setAuthData: (token: string, user: User) => {
        setToken(token)
        setUser(user)
        set({
          token,
          user,
          isAuthenticated: true,
        })
      },

      clearAuth: () => {
        removeToken()
        removeUser()
        set({
          token: null,
          user: null,
          isAuthenticated: false,
        })
      },
    }),
    {
      name: 'auth-storage',
      partialize: (state) => ({
        token: state.token,
        user: state.user,
        isAuthenticated: state.isAuthenticated,
      }),
    }
  )
)
