// 本地存储键名
const TOKEN_KEY = 'dw_dashboard_token'
const USER_KEY = 'dw_dashboard_user'
const SETTINGS_KEY = 'dw_dashboard_settings'

// Token相关
export const getToken = (): string | null => {
  return localStorage.getItem(TOKEN_KEY)
}

export const setToken = (token: string): void => {
  localStorage.setItem(TOKEN_KEY, token)
}

export const removeToken = (): void => {
  localStorage.removeItem(TOKEN_KEY)
}

// 用户信息相关
export const getUser = (): any => {
  const user = localStorage.getItem(USER_KEY)
  return user ? JSON.parse(user) : null
}

export const setUser = (user: any): void => {
  localStorage.setItem(USER_KEY, JSON.stringify(user))
}

export const removeUser = (): void => {
  localStorage.removeItem(USER_KEY)
}

// 设置相关
export const getSettings = (): any => {
  const settings = localStorage.getItem(SETTINGS_KEY)
  return settings ? JSON.parse(settings) : {}
}

export const setSettings = (settings: any): void => {
  localStorage.setItem(SETTINGS_KEY, JSON.stringify(settings))
}

export const removeSettings = (): void => {
  localStorage.removeItem(SETTINGS_KEY)
}

// 清除所有本地存储
export const clearStorage = (): void => {
  removeToken()
  removeUser()
  removeSettings()
}

// 通用存储方法
export const storage = {
  get(key: string): any {
    const value = localStorage.getItem(key)
    try {
      return value ? JSON.parse(value) : null
    } catch {
      return value
    }
  },

  set(key: string, value: any): void {
    const stringValue = typeof value === 'string' ? value : JSON.stringify(value)
    localStorage.setItem(key, stringValue)
  },

  remove(key: string): void {
    localStorage.removeItem(key)
  },

  clear(): void {
    localStorage.clear()
  },
}
