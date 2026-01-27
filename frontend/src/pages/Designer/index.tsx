import { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { Form, Input, Select, Button, Card, Space, message, InputNumber, Switch } from 'antd'
import { ArrowLeftOutlined, SaveOutlined } from '@ant-design/icons'
import type { Dashboard } from '@/types'
import { getDashboardDetail, createDashboard, updateDashboard } from '@/api/dashboard'
import { getAllDataSources } from '@/api/datasource'
import Loading from '@/components/Common/Loading'

const { TextArea } = Input

const Designer = () => {
  const { id } = useParams<{ id: string }>()
  const navigate = useNavigate()
  const [form] = Form.useForm()
  const [loading, setLoading] = useState(false)
  const [saving, setSaving] = useState(false)
  const [dataSources, setDataSources] = useState<any[]>([])

  useEffect(() => {
    loadDataSources()
    if (id) {
      loadDashboard()
    }
  }, [id])

  const loadDataSources = async () => {
    try {
      const result = await getAllDataSources()
      setDataSources(result)
    } catch (error: any) {
      message.error(error.message || '加载数据源失败')
    }
  }

  const loadDashboard = async () => {
    try {
      setLoading(true)
      const result = await getDashboardDetail(Number(id))
      form.setFieldsValue({
        name: result.name,
        description: result.description,
        category: result.category,
        datasourceId: result.datasourceId,
        isPublic: result.isPublic,
        sql: result.queryConfig.sql,
        enableCache: result.queryConfig.enableCache,
        cacheExpireSeconds: result.queryConfig.cacheExpireSeconds,
        enablePagination: result.queryConfig.enablePagination,
        timeout: result.queryConfig.timeout,
        chartType: result.displayConfig.chartType,
      })
    } catch (error: any) {
      message.error(error.message || '加载失败')
    } finally {
      setLoading(false)
    }
  }

  const handleSave = async () => {
    try {
      const values = await form.validateFields()
      setSaving(true)

      const data: Partial<Dashboard> = {
        name: values.name,
        description: values.description,
        category: values.category,
        datasourceId: values.datasourceId,
        isPublic: values.isPublic,
        queryConfig: {
          sql: values.sql,
          parameters: [],
          enableCache: values.enableCache,
          cacheExpireSeconds: values.cacheExpireSeconds,
          enablePagination: values.enablePagination,
          timeout: values.timeout,
        },
        displayConfig: {
          chartType: values.chartType,
          columns: [],
          filters: [],
        },
      }

      if (id) {
        await updateDashboard(Number(id), data)
        message.success('更新成功')
      } else {
        await createDashboard(data)
        message.success('创建成功')
      }

      navigate('/dashboard')
    } catch (error: any) {
      message.error(error.message || '保存失败')
    } finally {
      setSaving(false)
    }
  }

  if (loading) {
    return <Loading />
  }

  return (
    <div>
      <div style={{ marginBottom: 16 }}>
        <Space>
          <Button icon={<ArrowLeftOutlined />} onClick={() => navigate('/dashboard')}>
            返回
          </Button>
          <Button type="primary" icon={<SaveOutlined />} loading={saving} onClick={handleSave}>
            保存
          </Button>
        </Space>
      </div>

      <Card title={id ? '编辑报表' : '新建报表'}>
        <Form form={form} layout="vertical" initialValues={{ enableCache: true, cacheExpireSeconds: 300, timeout: 30, enablePagination: false, isPublic: false }}>
          <Form.Item
            label="报表名称"
            name="name"
            rules={[{ required: true, message: '请输入报表名称' }]}
          >
            <Input placeholder="请输入报表名称" />
          </Form.Item>

          <Form.Item label="描述" name="description">
            <TextArea rows={3} placeholder="请输入描述" />
          </Form.Item>

          <Form.Item
            label="分类"
            name="category"
            rules={[{ required: true, message: '请输入分类' }]}
          >
            <Input placeholder="请输入分类" />
          </Form.Item>

          <Form.Item
            label="数据源"
            name="datasourceId"
            rules={[{ required: true, message: '请选择数据源' }]}
          >
            <Select
              placeholder="请选择数据源"
              options={dataSources.map((ds) => ({ label: ds.name, value: ds.id }))}
            />
          </Form.Item>

          <Form.Item label="是否公开" name="isPublic" valuePropName="checked">
            <Switch />
          </Form.Item>

          <Form.Item
            label="SQL语句"
            name="sql"
            rules={[{ required: true, message: '请输入SQL语句' }]}
          >
            <TextArea rows={8} placeholder="请输入SQL语句" />
          </Form.Item>

          <Form.Item label="启用缓存" name="enableCache" valuePropName="checked">
            <Switch />
          </Form.Item>

          <Form.Item label="缓存过期时间(秒)" name="cacheExpireSeconds">
            <InputNumber min={0} style={{ width: '100%' }} />
          </Form.Item>

          <Form.Item label="启用分页" name="enablePagination" valuePropName="checked">
            <Switch />
          </Form.Item>

          <Form.Item label="超时时间(秒)" name="timeout">
            <InputNumber min={1} style={{ width: '100%' }} />
          </Form.Item>

          <Form.Item
            label="图表类型"
            name="chartType"
            rules={[{ required: true, message: '请选择图表类型' }]}
          >
            <Select
              placeholder="请选择图表类型"
              options={[
                { label: '表格', value: 'table' },
                { label: '折线图', value: 'line' },
                { label: '柱状图', value: 'bar' },
                { label: '饼图', value: 'pie' },
              ]}
            />
          </Form.Item>
        </Form>
      </Card>
    </div>
  )
}

export default Designer
