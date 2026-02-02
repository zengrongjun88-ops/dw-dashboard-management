import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.tsx'
import './index.css'

// 启用Mock模式（用于功能验证）
// 设置环境变量 VITE_USE_MOCK=true 或在URL中添加 ?mock=true 来启用
const useMock = import.meta.env.VITE_USE_MOCK === 'true' || window.location.search.includes('mock=true')

if (useMock) {
  import('./mock').then(({ enableMock }) => {
    enableMock()
    console.log('🎭 Mock模式已启用 - 所有API请求将使用Mock数据')
    console.log('💡 测试账号: admin / 123456')
  })
}

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
)
