import request from '@/utils/request';

export function create(data) {
    return request({
        url: 'joblist/create',
        method: 'post',
        data: data
    })
}

export function update(data) {
    return request({
        url: 'joblist/update',
        method: 'post',
        data: data
    })
}

export function del(ids) {
    return request({
        url: 'joblist/delete',
        method: 'post',
        params: {"ids": ids}
    })
}

export function detail(id) {
    return request({
        url: 'joblist/detail',
        method: 'post',
        params: {"id": id}
    })
}

export function query(data) {
    return request({
        url: 'joblist/query',
        method: 'post',
        data: data
    })
}

export function getByIdQuery(id) {
    return request({
        url: 'joblist/getByIdQuery',
        method: 'post',
        params: {"id": id}
    })
}