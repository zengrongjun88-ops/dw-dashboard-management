import { useState, useEffect } from 'react'
import { Table, Button, Space, Tag, Select, message, Modal, Form } from 'antd'
import { PlusOutlined, DeleteOutlined } from '@ant-design/icons'
import type { ColumnsType } from 'antd/es/table'
import type { DashboardPermission } from '@/types'
import { getPermissionList, grantUserPermission, grantRolePermission, revokePermission } from '@/api/permission'
import { getDashboardList } from '@/api/dashboard'
import { getUserList, getRoleList } from '@/api/user'
import { formatDateTime, formatPermissionLevel } from '@/utils/format'

const PermissionManage = () => {
  const [form] = Form.useForm()
  const [loading, setLoading] = useState(false)
  const [dataSource, setDataSource] = useState<DashboardPermission[]>([])
  const [total, setTotal] = useState(0)
  const [page, setPage] = useState(1)
  const [pageSize, setPageSize] = useState(10)
  const [modalVisible, setModalVisible] = useState(false)
  const [dashboards, setDashboards] = useState<any[]>([])
  const [users, setUsers] = useState<any[]>([])
  const [roles, setRoles] = useState<any[]>([])
  const [targetType, setTargetType] = useState<'user' | 'role'>('user')

  // 加载数据
  const loadData = async () => {
    try {
      setLoading(true)
      const result = await getPermissionList({
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

  // 加载报表列表
  const loadDashboards = async () => {
    try {
      const result = await getDashboardList({ page: 1, pageSize: 1000 })
      setDashboards(result.list)
    } catch (error: any) {
      message.error(error.message || '加载报表列表失败')
    }
  }

  // 加载用户列表
  const loadUsers = async () => {
    try {
      const result = await getUserList({ page: 1, pageSize: 1000 })
      setUsers(result.list)
    } catch (error: any) {
      message.error(error.message || '加载用户列表失败')
    }
  }

  // 加载角色列表
  const loadRoles = async () => {
    try {
      const result = await getRoleList({ page: 1, pageSize: 1000 })
      setRoles(result.list)
    } catch (error: any) {
      message.error(error.message || '加载角色列表失败')
    }
  }

  useEffect(() => {
    loadData()
    loadDashboards()
    loadUsers()
    loadRoles()
  }, [page, pageSize])

  // 打开授权弹窗
  const handleOpenModal = () => {
    form.resetFields()
    setModalVisible(true)
  }

  // 保存
  const handleSave = async () => {
    try {
      const values = await form.validateFields()
      if (values.targetType === 'user') {
        await grantUserPermission({
          dashboardId: values.dashboardId,
          userId: values.targetId,
          permissionLevel: values.permissionLevel,
        })
      } else {
        await grantRolePermission({
          dashboardId: values.dashboardId,
          roleId: values.targetId,
          permissionLevel: values.permissionLevel,
        })
      }
      message.success('授权成功')
      setModalVisible(false)
      loadData()
    } catch (error: any) {
      message.error(error.message || '授权失败')
    }
  }

  // 撤销权限
  const handleRevoke = (id: number) => {
    Modal.confirm({
      title: '确认撤销',
      content: '确定要撤销这个权限吗?',
      onOk: async () => {
        try {
          await revokePermission(id)
          message.success('撤销成功')
          loadData()
        } catch (error: any) {
          message.error(error.message || '撤销失败')
        }
      },
    })
  }

  // 表格列
  const columns: ColumnsType<DashboardPermission> = [
    {
      title: 'ID',
      dataIndex: 'id',
      width: 80,
    },
    {
      title: '报表ID',
      dataIndex: 'dashboardId',
      width: 100,
    },
    {
      title: '授权类型',
      dataIndex: 'targetType',
      width: 120,
      render: (type: string) => (
        <Tag color={type === 'user' ? 'blue' : 'green'}>
          {type === 'user' ? '用户' : '角色'}
        </Tag>
      ),
    },
    {
      title: '授权对象',
      dataIndex: 'targetName',
      width: 200,
    },
    {
      title: '权限级别',
      dataIndex: 'permissionLevel',
      width: 120,
      render: (level: string) => {
        const colorMap: Record<string, string> = {
          view: 'default',
          edit: 'processing',
          manage: 'success',
        }
        return <Tag color={colorMap[level]}>{formatPermissionLevel(level)}</Tag>
      },
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
      width: 120,
      fixed: 'right',
      render: (_, record) => (
        <Button
          type="link"
          size="small"
          danger
          icon={<DeleteOutlined />}
          onClick={() => handleRevoke(record.id)}
        >
          撤销
        </Button>
      ),
    },
  ]

  return (
    <div>
      <div style={{ marginBottom: 16, display: 'flex', justifyContent: 'flex-end' }}>
        <Button type="primary" icon={<PlusOutlined />} onClick={handleOpenModal}>
          授予权限
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
      />

      <Modal
        title="授予权限"
        open={modalVisible}
        onOk={handleSave}
        onCancel={() => setModalVisible(false)}
        width={600}
      >
        <Form form={form} layout="vertical">
          <Form.Item
            label="报表"
            name="dashboardId"
            rules={[{ required: true, message: '请选择报表' }]}
          >
            <Select
              placeholder="请选择报表"
              showSearch
              filterOption={(input, option) =>
                (option?.label ?? '').toLowerCase().includes(input.toLowerCase())
              }
              options={dashboards.map((d) => ({ label: d.name, value: d.id }))}
            />
          </Form.Item>

          <Form.Item
            label="授权类型"
            name="targetType"
            rules={[{ required: true, message: '请选择授权类型' }]}
            initialValue="user"
          >
            <Select
              placeholder="请选择授权类型"
              onChange={(value) => {
                setTargetType(value)
                form.setFieldValue('targetId', undefined)
              }}
              options={[
                { label: '用户', value: 'user' },
                { label: '角色', value: 'role' },
              ]}
            />
          </Form.Item>

          <Form.Item
            label="授权对象"
            name="targetId"
            rules={[{ required: true, message: '请选择授权对象' }]}
          >
            <Select
              placeholder="请选择授权对象"
              showSearch
              filterOption={(input, option) =>
                (option?.label ?? '').toLowerCase().includes(input.toLowerCase())
              }
              options={
                targetType === 'user'
                  ? users.map((u) => ({ label: u.realName || u.username, value: u.id }))
                  : roles.map((r) => ({ label: r.roleName, value: r.id }))
              }
            />
          </Form.Item>

          <Form.Item
            label="权限级别"
            name="permissionLevel"
            rules={[{ required: true, message: '请选择权限级别' }]}
          >
            <Select
              placeholder="请选择权限级别"
              options={[
                { label: '查看', value: 'view' },
                { label: '编辑', value: 'edit' },
                { label: '管理', value: 'manage' },
              ]}
            />
          </Form.Item>
        </Form>
      </Modal>
    </div>
  )
}

export default PermissionManage
