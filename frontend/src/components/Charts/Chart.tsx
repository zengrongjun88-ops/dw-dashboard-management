import ReactECharts from 'echarts-for-react'
import type { EChartsOption } from 'echarts'

interface ChartProps {
  option: EChartsOption
  style?: React.CSSProperties
  loading?: boolean
}

const Chart = ({ option, style = { height: '400px' }, loading = false }: ChartProps) => {
  return (
    <ReactECharts
      option={option}
      style={style}
      showLoading={loading}
      notMerge={true}
      lazyUpdate={true}
    />
  )
}

export default Chart
