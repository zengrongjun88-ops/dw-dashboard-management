import { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { Table, Button, Space, Tag, Input, Select, message, Modal } from 'antd'
import { PlusOutlined, EditOutlined, EyeOutlined, DeleteOutlined, SearchOutlined } from '@ant-design/icons'
import type { ColumnsType } from 'antd/es/table'
import type { Dashboard } from '@/types'
import { getDashboardList, deleteDashboard, publishDashboard, offlineDashboard } from '@/api/dashboard'
import { formatDateTime, formatStatus } from '@/utils/format'

const { Search } = Input

const DashboardList = () => {
  const navigate = useNavigate()
  const [loading, setLoading] = useState(false)
  const [dataSource, setDataSource] = useState<Dashboard[]>([])
  const [total, setTotal] = useState(0)
  const [page, setPage] = useState(1)
  const [pageSize, setPageSize] = useState(10)
  const [keyword, setKeyword] = useState('')
  const [status, setStatus] = useState<string>('')

  // 加载数据
  const loadData = async () => {
    try {
      setLoading(true)
      const result = await getDashboardList({
        page,
        pageSize,
        keyword,
        status,
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
  }, [page, pageSize, keyword, status])

  // 删除
  const handleDelete = (id: number) => {
    Modal.confirm({
      title: '确认删除',
      content: '确定要删除这个报表吗?',
      onOk: async () => {
        try {
          await deleteDashboard(id)
          message.success('删除成功')
          loadData()
        } catch (error: any) {
          message.error(error.message || '删除失败')
        }
      },
    })
  }

  // 发布
  const handlePublish = async (id: number) => {
    try {
      await publishDashboard(id)
      message.success('发布成功')
      loadData()
    } catch (error: any) {
      message.error(error.message || '发布失败')
    }
  }

  // 下线
  const handleOffline = async (id: number) => {
    try {
      await offlineDashboard(id)
      message.success('下线成功')
      loadData()
    } catch (error: any) {
      message.error(error.message || '下线失败')
    }
  }

  // 表格列
  const columns: ColumnsType<Dashboard> = [
    {
      title: 'ID',
      dataIndex: 'id',
      width: 80,
    },
    {
      title: '报表名称',
      dataIndex: 'name',
      width: 200,
    },
    {
      title: '描述',
      dataIndex: 'description',
      ellipsis: true,
    },
    {
      title: '分类',
      dataIndex: 'category',
      width: 120,
    },
    {
      title: '状态',
      dataIndex: 'status',
      width: 100,
      render: (status: string) => {
        const colorMap: Record<string, string> = {
          draft: 'default',
          published: 'success',
          offline: 'error',
        }
        return <Tag color={colorMap[status]}>{formatStatus(status)}</Tag>
      },
    },
    {
      title: '创建人',
      dataIndex: 'createUserName',
      width: 120,
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
            icon={<EyeOutlined />}
            onClick={() => navigate(`/dashboard/view/${record.id}`)}
          >
            查看
          </Button>
          <Button
            type="link"
            size="small"
            icon={<EditOutlined />}
            onClick={() => navigate(`/designer/${record.id}`)}
          >
            编辑
          </Button>
          {record.status === 'draft' && (
            <Button type="link" size="small" onClick={() => handlePublish(record.id)}>
              发布
            </Button>
          )}
          {record.status === 'published' && (
            <Button type="link" size="small" onClick={() => handleOffline(record.id)}>
              下线
            </Button>
          )}
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
        <Space>
          <Search
            placeholder="搜索报表名称"
            allowClear
            style={{ width: 300 }}
            onSearch={setKeyword}
          />
          <Select
            placeholder="状态"
            allowClear
            style={{ width: 120 }}
            onChange={setStatus}
            options={[
              { label: '草稿', value: 'draft' },
              { label: '已发布', value: 'published' },
              { label: '已下线', value: 'offline' },
            ]}
          />
        </Space>
        <Button
          type="primary"
          icon={<PlusOutlined />}
          onClick={() => navigate('/designer')}
        >
          新建报表
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

export default DashboardList
