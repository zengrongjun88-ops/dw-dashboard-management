import { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { Card, Button, Space, Form, Input, Select, DatePicker, Table, message } from 'antd'
import { ArrowLeftOutlined, SearchOutlined } from '@ant-design/icons'
import type { Dashboard } from '@/types'
import { getDashboardDetail, executeDashboard } from '@/api/dashboard'
import Chart from '@/components/Charts/Chart'
import Loading from '@/components/Common/Loading'

const DashboardView = () => {
  const { id } = useParams<{ id: string }>()
  const navigate = useNavigate()
  const [form] = Form.useForm()
  const [loading, setLoading] = useState(false)
  const [executing, setExecuting] = useState(false)
  const [dashboard, setDashboard] = useState<Dashboard | null>(null)
  const [data, setData] = useState<any[]>([])

  useEffect(() => {
    if (id) {
      loadDashboard()
    }
  }, [id])

  const loadDashboard = async () => {
    try {
      setLoading(true)
      const result = await getDashboardDetail(Number(id))
      setDashboard(result)
      // 自动执行查询
      executeQuery({})
    } catch (error: any) {
      message.error(error.message || '加载失败')
    } finally {
      setLoading(false)
    }
  }

  const executeQuery = async (parameters: Record<string, any>) => {
    try {
      setExecuting(true)
      const result = await executeDashboard({
        dashboardId: Number(id),
        parameters,
      })
      setData(result)
    } catch (error: any) {
      message.error(error.message || '查询失败')
    } finally {
      setExecuting(false)
    }
  }

  const handleSearch = () => {
    const values = form.getFieldsValue()
    executeQuery(values)
  }

  if (loading) {
    return <Loading />
  }

  if (!dashboard) {
    return null
  }

  // 渲染参数表单
  const renderParameterForm = () => {
    if (!dashboard.queryConfig.parameters || dashboard.queryConfig.parameters.length === 0) {
      return null
    }

    return (
      <Card title="查询参数" style={{ marginBottom: 16 }}>
        <Form form={form} layout="inline">
          {dashboard.queryConfig.parameters.map((param) => (
            <Form.Item
              key={param.name}
              name={param.name}
              label={param.label}
              rules={[{ required: param.required, message: `请输入${param.label}` }]}
            >
              {param.type === 'select' ? (
                <Select
                  style={{ width: 200 }}
                  placeholder={`请选择${param.label}`}
                  options={param.options}
                />
              ) : param.type === 'date' ? (
                <DatePicker style={{ width: 200 }} />
              ) : param.type === 'datetime' ? (
                <DatePicker showTime style={{ width: 200 }} />
              ) : (
                <Input style={{ width: 200 }} placeholder={`请输入${param.label}`} />
              )}
            </Form.Item>
          ))}
          <Form.Item>
            <Button type="primary" icon={<SearchOutlined />} onClick={handleSearch}>
              查询
            </Button>
          </Form.Item>
        </Form>
      </Card>
    )
  }

  // 渲染数据展示
  const renderData = () => {
    if (executing) {
      return <Loading tip="查询中..." />
    }

    if (!data || data.length === 0) {
      return <div style={{ textAlign: 'center', padding: 40 }}>暂无数据</div>
    }

    // 表格展示
    if (dashboard.displayConfig.chartType === 'table') {
      const columns = dashboard.displayConfig.columns.map((col) => ({
        title: col.title,
        dataIndex: col.field,
        key: col.field,
        width: col.width,
        align: col.align,
        fixed: col.fixed,
      }))

      return <Table dataSource={data} columns={columns} rowKey={(_, index) => index!} />
    }

    // 图表展示
    const chartOption = {
      ...dashboard.displayConfig.chartOptions,
      dataset: {
        source: data,
      },
    }

    return <Chart option={chartOption} />
  }

  return (
    <div>
      <div style={{ marginBottom: 16 }}>
        <Space>
          <Button icon={<ArrowLeftOutlined />} onClick={() => navigate('/dashboard')}>
            返回
          </Button>
        </Space>
      </div>

      <Card title={dashboard.name}>
        {renderParameterForm()}
        {renderData()}
      </Card>
    </div>
  )
}

export default DashboardView
