export type Status='SUBMITTED'|'PENDING_APPROVAL'|'APPROVED'|'REJECTED'
export type Leave={id:number;employeeName:string;employeeEmail:string;leaveType:string;startDate:string;endDate:string;days:number;reason?:string;status:Status;statusMessage?:string}
const call=async<T>(url:string,init?:RequestInit):Promise<T>=>{const r=await fetch(url,{headers:{'Content-Type':'application/json'},...init});if(!r.ok){const t=await r.text();throw new Error(t||r.statusText)}return r.status===204?undefined as T:r.json()}
export const submit=(v:Omit<Leave,'id'|'days'|'status'>)=>call<Leave>('/api/leaves',{method:'POST',body:JSON.stringify(v)})
export const mine=(email:string)=>call<Leave[]>(`/api/leaves?employeeEmail=${encodeURIComponent(email)}`)
export const pending=()=>call<Leave[]>('/api/leaves/pending')
export const balance=(email:string)=>call<{remainingDays:number}>(`/api/leaves/balance?employeeEmail=${encodeURIComponent(email)}`)
export const decide=(id:number,approved:boolean,comment:string)=>call<void>(`/api/leaves/${id}/decision`,{method:'POST',body:JSON.stringify({approved,comment})})
