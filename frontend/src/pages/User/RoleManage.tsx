import { useState, useEffect } from 'react'
import { Table, Card, message } from 'antd'
import type { ColumnsType } from 'antd/es/table'
import type { Role } from '@/types'
import { getRoleList } from '@/api/user'
import { formatDateTime } from '@/utils/format'

const UserRoleManage = () => {
  const [loading, setLoading] = useState(false)
  const [dataSource, setDataSource] = useState<Role[]>([])
  const [total, setTotal] = useState(0)
  const [page, setPage] = useState(1)
  const [pageSize, setPageSize] = useState(10)

  // 加载数据
  const loadData = async () => {
    try {
      setLoading(true)
      const result = await getRoleList({
        page,
        pageSize,
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
  }, [page, pageSize])

  // 表格列
  const columns: ColumnsType<Role> = [
    {
      title: 'ID',
      dataIndex: 'id',
      width: 80,
    },
    {
      title: '角色名称',
      dataIndex: 'roleName',
      width: 200,
    },
    {
      title: '角色编码',
      dataIndex: 'roleCode',
      width: 200,
    },
    {
      title: '描述',
      dataIndex: 'description',
      ellipsis: true,
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
      width: 180,
      render: (text: string) => formatDateTime(text),
    },
  ]

  return (
    <Card title="角色管理">
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
      />
    </Card>
  )
}

export default UserRoleManage
