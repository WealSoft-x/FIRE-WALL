export interface ReducerState {
    searchItems: SearchItems
}

interface SearchItems {
    title: string;
    shipNo: string;
}