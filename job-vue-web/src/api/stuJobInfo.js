import request from '@/utils/request';

export function create(data) {
    return request({
        url: 'stuJobInfo/create',
        method: 'post',
        data: data
    })
}

export function update(data) {
    return request({
        url: 'stuJobInfo/update',
        method: 'post',
        data: data
    })
}

export function del(ids) {
    return request({
        url: 'stuJobInfo/delete',
        method: 'post',
        params: {"ids": ids}
    })
}

export function detail(id) {
    return request({
        url: 'stuJobInfo/detail',
        method: 'post',
        params: {"id": id}
    })
}

export function query(data) {
    return request({
        url: 'stuJobInfo/query',
        method: 'post',
        data: data
    })
}

export function stuJobInfoInfo(data) {
    return request({
        url: 'stuJobInfo/info',
        method: 'post',
        data: data
    })
}