import { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { Card, Descriptions, Tag, Button, Space, message } from 'antd'
import { ArrowLeftOutlined, EditOutlined } from '@ant-design/icons'
import type { Dashboard } from '@/types'
import { getDashboardDetail } from '@/api/dashboard'
import { formatDateTime, formatStatus } from '@/utils/format'
import Loading from '@/components/Common/Loading'

const DashboardDetail = () => {
  const { id } = useParams<{ id: string }>()
  const navigate = useNavigate()
  const [loading, setLoading] = useState(false)
  const [dashboard, setDashboard] = useState<Dashboard | null>(null)

  useEffect(() => {
    if (id) {
      loadData()
    }
  }, [id])

  const loadData = async () => {
    try {
      setLoading(true)
      const result = await getDashboardDetail(Number(id))
      setDashboard(result)
    } catch (error: any) {
      message.error(error.message || '加载失败')
    } finally {
      setLoading(false)
    }
  }

  if (loading) {
    return <Loading />
  }

  if (!dashboard) {
    return null
  }

  return (
    <div>
      <div style={{ marginBottom: 16 }}>
        <Space>
          <Button icon={<ArrowLeftOutlined />} onClick={() => navigate('/dashboard')}>
            返回
          </Button>
          <Button
            type="primary"
            icon={<EditOutlined />}
            onClick={() => navigate(`/designer/${id}`)}
          >
            编辑
          </Button>
        </Space>
      </div>

      <Card title="基本信息">
        <Descriptions column={2}>
          <Descriptions.Item label="报表名称">{dashboard.name}</Descriptions.Item>
          <Descriptions.Item label="状态">
            <Tag color={dashboard.status === 'published' ? 'success' : 'default'}>
              {formatStatus(dashboard.status)}
            </Tag>
          </Descriptions.Item>
          <Descriptions.Item label="分类">{dashboard.category}</Descriptions.Item>
          <Descriptions.Item label="是否公开">
            {dashboard.isPublic ? '是' : '否'}
          </Descriptions.Item>
          <Descriptions.Item label="数据源">{dashboard.datasourceName}</Descriptions.Item>
          <Descriptions.Item label="创建人">{dashboard.createUserName}</Descriptions.Item>
          <Descriptions.Item label="创建时间">
            {formatDateTime(dashboard.createTime)}
          </Descriptions.Item>
          <Descriptions.Item label="更新时间">
            {formatDateTime(dashboard.updateTime)}
          </Descriptions.Item>
          <Descriptions.Item label="描述" span={2}>
            {dashboard.description}
          </Descriptions.Item>
          <Descriptions.Item label="标签" span={2}>
            {dashboard.tags?.map((tag) => (
              <Tag key={tag}>{tag}</Tag>
            ))}
          </Descriptions.Item>
        </Descriptions>
      </Card>

      <Card title="查询配置" style={{ marginTop: 16 }}>
        <Descriptions column={2}>
          <Descriptions.Item label="启用缓存">
            {dashboard.queryConfig.enableCache ? '是' : '否'}
          </Descriptions.Item>
          <Descriptions.Item label="缓存过期时间">
            {dashboard.queryConfig.cacheExpireSeconds}秒
          </Descriptions.Item>
          <Descriptions.Item label="启用分页">
            {dashboard.queryConfig.enablePagination ? '是' : '否'}
          </Descriptions.Item>
          <Descriptions.Item label="超时时间">
            {dashboard.queryConfig.timeout}秒
          </Descriptions.Item>
          <Descriptions.Item label="SQL语句" span={2}>
            <pre style={{ background: '#f5f5f5', padding: 12, borderRadius: 4 }}>
              {dashboard.queryConfig.sql}
            </pre>
          </Descriptions.Item>
        </Descriptions>
      </Card>

      <Card title="展示配置" style={{ marginTop: 16 }}>
        <Descriptions column={1}>
          <Descriptions.Item label="图表类型">
            {dashboard.displayConfig.chartType}
          </Descriptions.Item>
          <Descriptions.Item label="列配置">
            {dashboard.displayConfig.columns.length} 列
          </Descriptions.Item>
          <Descriptions.Item label="筛选器">
            {dashboard.displayConfig.filters.length} 个
          </Descriptions.Item>
        </Descriptions>
      </Card>
    </div>
  )
}

export default DashboardDetail
