import { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { Form, Input, Select, Button, Card, Space, message, InputNumber } from 'antd'
import { ArrowLeftOutlined, SaveOutlined, CheckCircleOutlined } from '@ant-design/icons'
import type { DataSource } from '@/types'
import { getDataSourceDetail, createDataSource, updateDataSource, testDataSourceConnection } from '@/api/datasource'
import Loading from '@/components/Common/Loading'

const { TextArea } = Input

const DataSourceCreate = () => {
  const { id } = useParams<{ id: string }>()
  const navigate = useNavigate()
  const [form] = Form.useForm()
  const [loading, setLoading] = useState(false)
  const [saving, setSaving] = useState(false)
  const [testing, setTesting] = useState(false)

  useEffect(() => {
    if (id) {
      loadDataSource()
    }
  }, [id])

  const loadDataSource = async () => {
    try {
      setLoading(true)
      const result = await getDataSourceDetail(Number(id))
      form.setFieldsValue(result)
    } catch (error: any) {
      message.error(error.message || '加载失败')
    } finally {
      setLoading(false)
    }
  }

  const handleTest = async () => {
    try {
      const values = await form.validateFields()
      setTesting(true)
      const result = await testDataSourceConnection(values)
      if (result.success) {
        message.success('连接测试成功')
      } else {
        message.error(result.message || '连接测试失败')
      }
    } catch (error: any) {
      message.error(error.message || '连接测试失败')
    } finally {
      setTesting(false)
    }
  }

  const handleSave = async () => {
    try {
      const values = await form.validateFields()
      setSaving(true)

      if (id) {
        await updateDataSource(Number(id), values)
        message.success('更新成功')
      } else {
        await createDataSource(values)
        message.success('创建成功')
      }

      navigate('/datasource')
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
          <Button icon={<ArrowLeftOutlined />} onClick={() => navigate('/datasource')}>
            返回
          </Button>
          <Button icon={<CheckCircleOutlined />} loading={testing} onClick={handleTest}>
            测试连接
          </Button>
          <Button type="primary" icon={<SaveOutlined />} loading={saving} onClick={handleSave}>
            保存
          </Button>
        </Space>
      </div>

      <Card title={id ? '编辑数据源' : '新建数据源'}>
        <Form form={form} layout="vertical" initialValues={{ port: 3306, status: 1 }}>
          <Form.Item
            label="数据源名称"
            name="name"
            rules={[{ required: true, message: '请输入数据源名称' }]}
          >
            <Input placeholder="请输入数据源名称" />
          </Form.Item>

          <Form.Item
            label="数据源类型"
            name="type"
            rules={[{ required: true, message: '请选择数据源类型' }]}
          >
            <Select
              placeholder="请选择数据源类型"
              options={[
                { label: 'MySQL', value: 'mysql' },
                { label: 'PostgreSQL', value: 'postgresql' },
                { label: 'Oracle', value: 'oracle' },
                { label: 'SQL Server', value: 'sqlserver' },
                { label: 'ClickHouse', value: 'clickhouse' },
              ]}
            />
          </Form.Item>

          <Form.Item
            label="主机地址"
            name="host"
            rules={[{ required: true, message: '请输入主机地址' }]}
          >
            <Input placeholder="请输入主机地址" />
          </Form.Item>

          <Form.Item
            label="端口"
            name="port"
            rules={[{ required: true, message: '请输入端口' }]}
          >
            <InputNumber min={1} max={65535} style={{ width: '100%' }} />
          </Form.Item>

          <Form.Item
            label="数据库名"
            name="database"
            rules={[{ required: true, message: '请输入数据库名' }]}
          >
            <Input placeholder="请输入数据库名" />
          </Form.Item>

          <Form.Item
            label="用户名"
            name="username"
            rules={[{ required: true, message: '请输入用户名' }]}
          >
            <Input placeholder="请输入用户名" />
          </Form.Item>

          <Form.Item
            label="密码"
            name="password"
            rules={[{ required: !id, message: '请输入密码' }]}
          >
            <Input.Password placeholder={id ? '不修改请留空' : '请输入密码'} />
          </Form.Item>

          <Form.Item label="描述" name="description">
            <TextArea rows={3} placeholder="请输入描述" />
          </Form.Item>
        </Form>
      </Card>
    </div>
  )
}

export default DataSourceCreate
