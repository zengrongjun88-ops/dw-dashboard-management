import { create } from 'zustand'
import type { User, Role } from '@/types'

interface UserState {
  users: User[]
  roles: Role[]
  currentUser: User | null
  loading: boolean

  // Actions
  setUsers: (users: User[]) => void
  setRoles: (roles: Role[]) => void
  setCurrentUser: (user: User | null) => void
  setLoading: (loading: boolean) => void
  addUser: (user: User) => void
  updateUser: (id: number, user: Partial<User>) => void
  removeUser: (id: number) => void
}

export const useUserStore = create<UserState>((set) => ({
  users: [],
  roles: [],
  currentUser: null,
  loading: false,

  setUsers: (users) => set({ users }),
  setRoles: (roles) => set({ roles }),
  setCurrentUser: (user) => set({ currentUser: user }),
  setLoading: (loading) => set({ loading }),

  addUser: (user) =>
    set((state) => ({
      users: [...state.users, user],
    })),

  updateUser: (id, updatedUser) =>
    set((state) => ({
      users: state.users.map((user) => (user.id === id ? { ...user, ...updatedUser } : user)),
    })),

  removeUser: (id) =>
    set((state) => ({
      users: state.users.filter((user) => user.id !== id),
    })),
}))
