import { create } from 'zustand'
import type { Dashboard, DataSource } from '@/types'

interface DashboardState {
  dashboards: Dashboard[]
  dataSources: DataSource[]
  currentDashboard: Dashboard | null
  loading: boolean

  // Actions
  setDashboards: (dashboards: Dashboard[]) => void
  setDataSources: (dataSources: DataSource[]) => void
  setCurrentDashboard: (dashboard: Dashboard | null) => void
  setLoading: (loading: boolean) => void
  addDashboard: (dashboard: Dashboard) => void
  updateDashboard: (id: number, dashboard: Partial<Dashboard>) => void
  removeDashboard: (id: number) => void
}

export const useDashboardStore = create<DashboardState>((set) => ({
  dashboards: [],
  dataSources: [],
  currentDashboard: null,
  loading: false,

  setDashboards: (dashboards) => set({ dashboards }),
  setDataSources: (dataSources) => set({ dataSources }),
  setCurrentDashboard: (dashboard) => set({ currentDashboard: dashboard }),
  setLoading: (loading) => set({ loading }),

  addDashboard: (dashboard) =>
    set((state) => ({
      dashboards: [...state.dashboards, dashboard],
    })),

  updateDashboard: (id, updatedDashboard) =>
    set((state) => ({
      dashboards: state.dashboards.map((dashboard) =>
        dashboard.id === id ? { ...dashboard, ...updatedDashboard } : dashboard
      ),
    })),

  removeDashboard: (id) =>
    set((state) => ({
      dashboards: state.dashboards.filter((dashboard) => dashboard.id !== id),
    })),
}))
