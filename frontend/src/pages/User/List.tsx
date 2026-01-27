import { useState, useEffect } from 'react'
import { Table, Button, Space, Tag, Input, message, Modal, Form, Select, Switch } from 'antd'
import { PlusOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons'
import type { ColumnsType } from 'antd/es/table'
import type { User, Role } from '@/types'
import { getUserList, deleteUser, createUser, updateUser, updateUserStatus, getAllRoles } from '@/api/user'
import { formatDateTime } from '@/utils/format'

const { Search } = Input

const UserList = () => {
  const [form] = Form.useForm()
  const [loading, setLoading] = useState(false)
  const [dataSource, setDataSource] = useState<User[]>([])
  const [roles, setRoles] = useState<Role[]>([])
  const [total, setTotal] = useState(0)
  const [page, setPage] = useState(1)
  const [pageSize, setPageSize] = useState(10)
  const [keyword, setKeyword] = useState('')
  const [modalVisible, setModalVisible] = useState(false)
  const [editingUser, setEditingUser] = useState<User | null>(null)

  // 加载数据
  const loadData = async () => {
    try {
      setLoading(true)
      const result = await getUserList({
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

  // 加载角色
  const loadRoles = async () => {
    try {
      const result = await getAllRoles()
      setRoles(result)
    } catch (error: any) {
      message.error(error.message || '加载角色失败')
    }
  }

  useEffect(() => {
    loadData()
    loadRoles()
  }, [page, pageSize, keyword])

  // 打开新建/编辑弹窗
  const handleOpenModal = (user?: User) => {
    setEditingUser(user || null)
    if (user) {
      form.setFieldsValue(user)
    } else {
      form.resetFields()
    }
    setModalVisible(true)
  }

  // 保存
  const handleSave = async () => {
    try {
      const values = await form.validateFields()
      if (editingUser) {
        await updateUser(editingUser.id, values)
        message.success('更新成功')
      } else {
        await createUser(values)
        message.success('创建成功')
      }
      setModalVisible(false)
      loadData()
    } catch (error: any) {
      message.error(error.message || '保存失败')
    }
  }

  // 删除
  const handleDelete = (id: number) => {
    Modal.confirm({
      title: '确认删除',
      content: '确定要删除这个用户吗?',
      onOk: async () => {
        try {
          await deleteUser(id)
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
      await updateUserStatus(id, status)
      message.success('状态修改成功')
      loadData()
    } catch (error: any) {
      message.error(error.message || '状态修改失败')
    }
  }

  // 表格列
  const columns: ColumnsType<User> = [
    {
      title: 'ID',
      dataIndex: 'id',
      width: 80,
    },
    {
      title: '用户名',
      dataIndex: 'username',
      width: 150,
    },
    {
      title: '真实姓名',
      dataIndex: 'realName',
      width: 150,
    },
    {
      title: '邮箱',
      dataIndex: 'email',
      width: 200,
    },
    {
      title: '手机号',
      dataIndex: 'phone',
      width: 150,
    },
    {
      title: '角色',
      dataIndex: 'roleNames',
      width: 200,
      render: (roleNames: string[]) => (
        <>
          {roleNames?.map((name) => (
            <Tag key={name}>{name}</Tag>
          ))}
        </>
      ),
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
      width: 180,
      fixed: 'right',
      render: (_, record) => (
        <Space>
          <Button
            type="link"
            size="small"
            icon={<EditOutlined />}
            onClick={() => handleOpenModal(record)}
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
          placeholder="搜索用户名或姓名"
          allowClear
          style={{ width: 300 }}
          onSearch={setKeyword}
        />
        <Button type="primary" icon={<PlusOutlined />} onClick={() => handleOpenModal()}>
          新建用户
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
        scroll={{ x: 1400 }}
      />

      <Modal
        title={editingUser ? '编辑用户' : '新建用户'}
        open={modalVisible}
        onOk={handleSave}
        onCancel={() => setModalVisible(false)}
        width={600}
      >
        <Form form={form} layout="vertical" initialValues={{ status: 1 }}>
          <Form.Item
            label="用户名"
            name="username"
            rules={[{ required: true, message: '请输入用户名' }]}
          >
            <Input placeholder="请输入用户名" disabled={!!editingUser} />
          </Form.Item>

          {!editingUser && (
            <Form.Item
              label="密码"
              name="password"
              rules={[{ required: true, message: '请输入密码' }]}
            >
              <Input.Password placeholder="请输入密码" />
            </Form.Item>
          )}

          <Form.Item
            label="真实姓名"
            name="realName"
            rules={[{ required: true, message: '请输入真实姓名' }]}
          >
            <Input placeholder="请输入真实姓名" />
          </Form.Item>

          <Form.Item
            label="邮箱"
            name="email"
            rules={[
              { required: true, message: '请输入邮箱' },
              { type: 'email', message: '请输入有效的邮箱' },
            ]}
          >
            <Input placeholder="请输入邮箱" />
          </Form.Item>

          <Form.Item label="手机号" name="phone">
            <Input placeholder="请输入手机号" />
          </Form.Item>

          <Form.Item label="角色" name="roleIds">
            <Select
              mode="multiple"
              placeholder="请选择角色"
              options={roles.map((role) => ({ label: role.roleName, value: role.id }))}
            />
          </Form.Item>
        </Form>
      </Modal>
    </div>
  )
}

export default UserList
