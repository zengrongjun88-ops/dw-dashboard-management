import { Spin } from 'antd'

interface LoadingProps {
  tip?: string
  size?: 'small' | 'default' | 'large'
}

const Loading = ({ tip = '加载中...', size = 'large' }: LoadingProps) => {
  return (
    <div
      style={{
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        minHeight: '400px',
      }}
    >
      <Spin size={size} tip={tip} />
    </div>
  )
}

export default Loading
