import { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { Table, Button, Space, Tag, Input, message, Modal, Switch } from 'antd'
import { PlusOutlined, EditOutlined, DeleteOutlined, CheckCircleOutlined } from '@ant-design/icons'
import type { ColumnsType } from 'antd/es/table'
import type { DataSource } from '@/types'
import { getDataSourceList, deleteDataSource, updateDataSourceStatus, testDataSourceConnection } from '@/api/datasource'
import { formatDateTime, formatDataSourceType } from '@/utils/format'

const { Search } = Input

const DataSourceList = () => {
  const navigate = useNavigate()
  const [loading, setLoading] = useState(false)
  const [dataSource, setDataSource] = useState<DataSource[]>([])
  const [total, setTotal] = useState(0)
  const [page, setPage] = useState(1)
  const [pageSize, setPageSize] = useState(10)
  const [keyword, setKeyword] = useState('')

  // 加载数据
  const loadData = async () => {
    try {
      setLoading(true)
      const result = await getDataSourceList({
        page,
        pageSize,
        keyword,
      })
      setDataSource(result.list)
      setTotal(result.total)
    } catch (error: any) {
      message.error(error.message || '加载失败')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    loadData()
  }, [page, pageSize, keyword])

  // 删除
  const handleDelete = (id: number) => {
    Modal.confirm({
      title: '确认删除',
      content: '确定要删除这个数据源吗?',
      onOk: async () => {
        try {
          await deleteDataSource(id)
          message.success('删除成功')
          loadData()
        } catch (error: any) {
          message.error(error.message || '删除失败')
        }
      },
    })
  }

  // 修改状态
  const handleStatusChange = async (id: number, status: number) => {
    try {
      await updateDataSourceStatus(id, status)
      message.success('状态修改成功')
      loadData()
    } catch (error: any) {
      message.error(error.message || '状态修改失败')
    }
  }

  // 测试连接
  const handleTest = async (record: DataSource) => {
    try {
      const result = await testDataSourceConnection(record)
      if (result.success) {
        message.success('连接测试成功')
      } else {
        message.error(result.message || '连接测试失败')
      }
    } catch (error: any) {
      message.error(error.message || '连接测试失败')
    }
  }

  // 表格列
  const columns: ColumnsType<DataSource> = [
    {
      title: 'ID',
      dataIndex: 'id',
      width: 80,
    },
    {
      title: '数据源名称',
      dataIndex: 'name',
      width: 200,
    },
    {
      title: '类型',
      dataIndex: 'type',
      width: 120,
      render: (type: string) => formatDataSourceType(type),
    },
    {
      title: '主机',
      dataIndex: 'host',
      width: 150,
    },
    {
      title: '端口',
      dataIndex: 'port',
      width: 80,
    },
    {
      title: '数据库',
      dataIndex: 'database',
      width: 150,
    },
    {
      title: '状态',
      dataIndex: 'status',
      width: 100,
      render: (status: number, record) => (
        <Switch
          checked={status === 1}
          onChange={(checked) => handleStatusChange(record.id, checked ? 1 : 0)}
        />
      ),
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
      width: 180,
      render: (text: string) => formatDateTime(text),
    },
    {
      title: '操作',
      key: 'action',
      width: 250,
      fixed: 'right',
      render: (_, record) => (
        <Space>
          <Button
            type="link"
            size="small"
            icon={<CheckCircleOutlined />}
            onClick={() => handleTest(record)}
          >
            测试
          </Button>
          <Button
            type="link"
            size="small"
            icon={<EditOutlined />}
            onClick={() => navigate(`/datasource/edit/${record.id}`)}
          >
            编辑
          </Button>
          <Button
            type="link"
            size="small"
            danger
            icon={<DeleteOutlined />}
            onClick={() => handleDelete(record.id)}
          >
            删除
          </Button>
        </Space>
      ),
    },
  ]

  return (
    <div>
      <div style={{ marginBottom: 16, display: 'flex', justifyContent: 'space-between' }}>
        <Search
          placeholder="搜索数据源名称"
          allowClear
          style={{ width: 300 }}
          onSearch={setKeyword}
        />
        <Button
          type="primary"
          icon={<PlusOutlined />}
          onClick={() => navigate('/datasource/create')}
        >
          新建数据源
        </Button>
      </div>

      <Table
        loading={loading}
        dataSource={dataSource}
        columns={columns}
        rowKey="id"
        pagination={{
          current: page,
          pageSize,
          total,
          showSizeChanger: true,
          showQuickJumper: true,
          showTotal: (total) => `共 ${total} 条`,
          onChange: (page, pageSize) => {
            setPage(page)
            setPageSize(pageSize)
          },
        }}
        scroll={{ x: 1200 }}
      />
    </div>
  )
}

export default DataSourceList
