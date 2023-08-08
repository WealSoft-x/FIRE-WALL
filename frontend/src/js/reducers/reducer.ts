import Immutable from 'immutable'
import { ReducerState } from '../_type/state'

const initialState: ReducerState = {
  searchItems: {
    title: '',
    shipNo: '',
  },
}

/**
 * Reducer Reducer
 *
 * @param state
 * @param action
 * @return {any |*}
 */
export default (state: ReducerState = initialState, action: any = {}): ReducerState => {
    let prevState: any = Immutable.fromJS(state);
    let nextState: any = {};

  switch (action.type) {
    case '':
      nextState = prevState.merge({
        searchItems: action.searchItems,
      })
      break
    default:
      nextState = prevState
  }
  return nextState.toJS()
}
