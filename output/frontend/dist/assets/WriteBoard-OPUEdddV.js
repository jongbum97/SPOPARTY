import{_ as F,j as N,u as R,r as d,i as U,f as t,e as a,F as j,b as o,o as y,g as i,z as W,A as $,h as z}from"./index-w_VBQHr5.js";import{u as A,_ as D}from"./TextEditor-0PeRoz14.js";const E=s=>(W("data-v-a400887a"),s=s(),$(),s),T=E(()=>z("h1",null,"게시글 작성",-1)),q={__name:"WriteBoard",setup(s){const m=A(),f=N(),v=R(),b=localStorage.getItem("id"),p=f.params.clubId,c=d(""),u=d(""),r=d([]),V=[e=>!!e||"제목을 필수입니다!",e=>e&&e.length<=40||"제목은 40자 이하로 작성해주세요"],x=()=>{const e=new FormData;e.append("memberId",b),e.append("clubId",p),e.append("title",c.value),e.append("content",u.value),e.append("file",r.value[0]),confirm("게시글을 작성하시겠습니까??")===!0&&m.createBoard(e)},I=()=>{v.push(`/club/${p}/board`)};return(e,l)=>{const w=o("v-text-field"),B=o("v-file-input"),g=o("v-card-text"),_=o("v-btn"),S=o("v-col"),k=o("v-row"),C=o("v-container"),h=o("v-icon");return y(),U(j,null,[t(C,{class:"write-board"},{default:a(()=>[T,t(w,{modelValue:c.value,"onUpdate:modelValue":l[0]||(l[0]=n=>c.value=n),rules:V,outlined:"",class:"title"},null,8,["modelValue"]),t(D,{class:"file-input",modelValue:u.value,"onUpdate:modelValue":l[1]||(l[1]=n=>u.value=n)},null,8,["modelValue"]),t(g,{class:"file-input-container"},{default:a(()=>[t(B,{accept:"image/*,.mp4",label:"파일을 첨부해주세요",modelValue:r.value,"onUpdate:modelValue":l[2]||(l[2]=n=>r.value=n),class:"file-input"},null,8,["modelValue"])]),_:1}),t(k,{justify:"end",style:{"margin-top":"10px"}},{default:a(()=>[t(S,{cols:"auto"},{default:a(()=>[t(_,{onClick:x},{default:a(()=>[i(" 저장하기 ")]),_:1})]),_:1})]),_:1})]),_:1}),t(_,{icon:"",class:"left-arrow",onClick:I},{default:a(()=>[t(h,null,{default:a(()=>[i("mdi-arrow-left")]),_:1})]),_:1})],64)}}},J=F(q,[["__scopeId","data-v-a400887a"]]);export{J as default};
