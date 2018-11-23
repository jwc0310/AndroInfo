package com.andy.androinfo.beans;

import java.util.List;

public class XAPKManifest {


    /**
     * xapk_version : 1
     * package_name : com.joycity.gnss
     * name : The War of Genesis
     * locales_name : {"af":"The War of Genesis","am":"The War of Genesis","ar":"The War of Genesis","az":"The War of Genesis","be":"The War of Genesis","bg":"The War of Genesis","bn":"The War of Genesis","bs":"The War of Genesis","ca":"The War of Genesis","cs":"The War of Genesis","da":"The War of Genesis","de":"The War of Genesis","el":"The War of Genesis","en":"The War of Genesis","en-AU":"The War of Genesis","en-CA":"The War of Genesis","en-GB":"The War of Genesis","en-IN":"The War of Genesis","en-XC":"The War of Genesis","es":"The War of Genesis","es-ES":"The War of Genesis","es-US":"The War of Genesis","et":"The War of Genesis","eu":"The War of Genesis","fa":"The War of Genesis","fi":"The War of Genesis","fr":"The War of Genesis","fr-CA":"The War of Genesis","gl":"The War of Genesis","gu":"The War of Genesis","hi":"The War of Genesis","hr":"The War of Genesis","hu":"The War of Genesis","hy":"The War of Genesis","id":"The War of Genesis","in":"The War of Genesis","is":"The War of Genesis","it":"The War of Genesis","iw":"The War of Genesis","ja":"創世記戦","ka":"The War of Genesis","kk":"The War of Genesis","km":"The War of Genesis","kn":"The War of Genesis","ko":"창세기전","ky":"The War of Genesis","lo":"The War of Genesis","lt":"The War of Genesis","lv":"The War of Genesis","mk":"The War of Genesis","ml":"The War of Genesis","mn":"The War of Genesis","mr":"The War of Genesis","ms":"The War of Genesis","my":"The War of Genesis","nb":"The War of Genesis","ne":"The War of Genesis","nl":"The War of Genesis","pa":"The War of Genesis","pl":"The War of Genesis","pt":"The War of Genesis","pt-BR":"The War of Genesis","pt-PT":"The War of Genesis","ro":"The War of Genesis","ru":"The War of Genesis","si":"The War of Genesis","sk":"The War of Genesis","sl":"The War of Genesis","sq":"The War of Genesis","sr":"The War of Genesis","sr-Latn":"The War of Genesis","sv":"The War of Genesis","sw":"The War of Genesis","ta":"The War of Genesis","te":"The War of Genesis","th":"The War of Genesis","tl":"The War of Genesis","tr":"The War of Genesis","uk":"The War of Genesis","ur":"The War of Genesis","uz":"The War of Genesis","vi":"The War of Genesis","zh-CN":"The War of Genesis","zh-HK":"The War of Genesis","zh-TW":"The War of Genesis","zu":"The War of Genesis"}
     * version_code : 37
     * version_name : 1174
     * min_sdk_version : 16
     * target_sdk_version : 26
     * permissions : ["com.google.android.c2dm.permission.RECEIVE","com.joycity.gnss.permission.C2D_MESSAGE","android.permission.INTERNET","android.permission.ACCESS_NETWORK_STATE","android.permission.ACCESS_WIFI_STATE","android.permission.WAKE_LOCK","android.permission.READ_EXTERNAL_STORAGE","android.permission.WRITE_EXTERNAL_STORAGE","com.android.vending.BILLING","com.google.android.finsky.permission.BIND_GET_INSTALL_REFERRER_SERVICE"]
     * total_size : 442976132
     * expansions : [{"file":"Android/obb/com.joycity.gnss/main.37.com.joycity.gnss.obb","install_location":"EXTERNAL_STORAGE","install_path":"Android/obb/com.joycity.gnss/main.37.com.joycity.gnss.obb"}]
     */

    private int xapk_version;
    private String package_name;
    private String name;
    private LocalesNameBean locales_name;
    private String version_code;
    private String version_name;
    private String min_sdk_version;
    private String target_sdk_version;
    private int total_size;
    private List<String> permissions;
    private List<ExpansionsBean> expansions;

    public int getXapk_version() {
        return xapk_version;
    }

    public void setXapk_version(int xapk_version) {
        this.xapk_version = xapk_version;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalesNameBean getLocales_name() {
        return locales_name;
    }

    public void setLocales_name(LocalesNameBean locales_name) {
        this.locales_name = locales_name;
    }

    public String getVersion_code() {
        return version_code;
    }

    public void setVersion_code(String version_code) {
        this.version_code = version_code;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public String getMin_sdk_version() {
        return min_sdk_version;
    }

    public void setMin_sdk_version(String min_sdk_version) {
        this.min_sdk_version = min_sdk_version;
    }

    public String getTarget_sdk_version() {
        return target_sdk_version;
    }

    public void setTarget_sdk_version(String target_sdk_version) {
        this.target_sdk_version = target_sdk_version;
    }

    public int getTotal_size() {
        return total_size;
    }

    public void setTotal_size(int total_size) {
        this.total_size = total_size;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public List<ExpansionsBean> getExpansions() {
        return expansions;
    }

    public void setExpansions(List<ExpansionsBean> expansions) {
        this.expansions = expansions;
    }

    @Override
    public String toString() {
        return "XAPKManifest{" +
                "xapk_version=" + xapk_version +
                ", package_name='" + package_name + '\'' +
                ", name='" + name + '\'' +
                ", locales_name=" + locales_name +
                ", version_code='" + version_code + '\'' +
                ", version_name='" + version_name + '\'' +
                ", min_sdk_version='" + min_sdk_version + '\'' +
                ", target_sdk_version='" + target_sdk_version + '\'' +
                ", total_size=" + total_size +
                ", permissions=" + permissions +
                ", expansions=" + expansions +
                '}';
    }

    public static class LocalesNameBean {
        /**
         * af : The War of Genesis
         * am : The War of Genesis
         * ar : The War of Genesis
         * az : The War of Genesis
         * be : The War of Genesis
         * bg : The War of Genesis
         * bn : The War of Genesis
         * bs : The War of Genesis
         * ca : The War of Genesis
         * cs : The War of Genesis
         * da : The War of Genesis
         * de : The War of Genesis
         * el : The War of Genesis
         * en : The War of Genesis
         * en-AU : The War of Genesis
         * en-CA : The War of Genesis
         * en-GB : The War of Genesis
         * en-IN : The War of Genesis
         * en-XC : The War of Genesis
         * es : The War of Genesis
         * es-ES : The War of Genesis
         * es-US : The War of Genesis
         * et : The War of Genesis
         * eu : The War of Genesis
         * fa : The War of Genesis
         * fi : The War of Genesis
         * fr : The War of Genesis
         * fr-CA : The War of Genesis
         * gl : The War of Genesis
         * gu : The War of Genesis
         * hi : The War of Genesis
         * hr : The War of Genesis
         * hu : The War of Genesis
         * hy : The War of Genesis
         * id : The War of Genesis
         * in : The War of Genesis
         * is : The War of Genesis
         * it : The War of Genesis
         * iw : The War of Genesis
         * ja : 創世記戦
         * ka : The War of Genesis
         * kk : The War of Genesis
         * km : The War of Genesis
         * kn : The War of Genesis
         * ko : 창세기전
         * ky : The War of Genesis
         * lo : The War of Genesis
         * lt : The War of Genesis
         * lv : The War of Genesis
         * mk : The War of Genesis
         * ml : The War of Genesis
         * mn : The War of Genesis
         * mr : The War of Genesis
         * ms : The War of Genesis
         * my : The War of Genesis
         * nb : The War of Genesis
         * ne : The War of Genesis
         * nl : The War of Genesis
         * pa : The War of Genesis
         * pl : The War of Genesis
         * pt : The War of Genesis
         * pt-BR : The War of Genesis
         * pt-PT : The War of Genesis
         * ro : The War of Genesis
         * ru : The War of Genesis
         * si : The War of Genesis
         * sk : The War of Genesis
         * sl : The War of Genesis
         * sq : The War of Genesis
         * sr : The War of Genesis
         * sr-Latn : The War of Genesis
         * sv : The War of Genesis
         * sw : The War of Genesis
         * ta : The War of Genesis
         * te : The War of Genesis
         * th : The War of Genesis
         * tl : The War of Genesis
         * tr : The War of Genesis
         * uk : The War of Genesis
         * ur : The War of Genesis
         * uz : The War of Genesis
         * vi : The War of Genesis
         * zh-CN : The War of Genesis
         * zh-HK : The War of Genesis
         * zh-TW : The War of Genesis
         * zu : The War of Genesis
         */

        private String af;
        private String am;
        private String ar;
        private String az;
        private String be;
        private String bg;
        private String bn;
        private String bs;
        private String ca;
        private String cs;
        private String da;
        private String de;
        private String el;
        private String en;
        @com.google.gson.annotations.SerializedName("en-AU")
        private String enAU;
        @com.google.gson.annotations.SerializedName("en-CA")
        private String enCA;
        @com.google.gson.annotations.SerializedName("en-GB")
        private String enGB;
        @com.google.gson.annotations.SerializedName("en-IN")
        private String enIN;
        @com.google.gson.annotations.SerializedName("en-XC")
        private String enXC;
        private String es;
        @com.google.gson.annotations.SerializedName("es-ES")
        private String esES;
        @com.google.gson.annotations.SerializedName("es-US")
        private String esUS;
        private String et;
        private String eu;
        private String fa;
        private String fi;
        private String fr;
        @com.google.gson.annotations.SerializedName("fr-CA")
        private String frCA;
        private String gl;
        private String gu;
        private String hi;
        private String hr;
        private String hu;
        private String hy;
        private String id;
        private String in;
        private String is;
        private String it;
        private String iw;
        private String ja;
        private String ka;
        private String kk;
        private String km;
        private String kn;
        private String ko;
        private String ky;
        private String lo;
        private String lt;
        private String lv;
        private String mk;
        private String ml;
        private String mn;
        private String mr;
        private String ms;
        private String my;
        private String nb;
        private String ne;
        private String nl;
        private String pa;
        private String pl;
        private String pt;
        @com.google.gson.annotations.SerializedName("pt-BR")
        private String ptBR;
        @com.google.gson.annotations.SerializedName("pt-PT")
        private String ptPT;
        private String ro;
        private String ru;
        private String si;
        private String sk;
        private String sl;
        private String sq;
        private String sr;
        @com.google.gson.annotations.SerializedName("sr-Latn")
        private String srLatn;
        private String sv;
        private String sw;
        private String ta;
        private String te;
        private String th;
        private String tl;
        private String tr;
        private String uk;
        private String ur;
        private String uz;
        private String vi;
        @com.google.gson.annotations.SerializedName("zh-CN")
        private String zhCN;
        @com.google.gson.annotations.SerializedName("zh-HK")
        private String zhHK;
        @com.google.gson.annotations.SerializedName("zh-TW")
        private String zhTW;
        private String zu;

        public String getAf() {
            return af;
        }

        public void setAf(String af) {
            this.af = af;
        }

        public String getAm() {
            return am;
        }

        public void setAm(String am) {
            this.am = am;
        }

        public String getAr() {
            return ar;
        }

        public void setAr(String ar) {
            this.ar = ar;
        }

        public String getAz() {
            return az;
        }

        public void setAz(String az) {
            this.az = az;
        }

        public String getBe() {
            return be;
        }

        public void setBe(String be) {
            this.be = be;
        }

        public String getBg() {
            return bg;
        }

        public void setBg(String bg) {
            this.bg = bg;
        }

        public String getBn() {
            return bn;
        }

        public void setBn(String bn) {
            this.bn = bn;
        }

        public String getBs() {
            return bs;
        }

        public void setBs(String bs) {
            this.bs = bs;
        }

        public String getCa() {
            return ca;
        }

        public void setCa(String ca) {
            this.ca = ca;
        }

        public String getCs() {
            return cs;
        }

        public void setCs(String cs) {
            this.cs = cs;
        }

        public String getDa() {
            return da;
        }

        public void setDa(String da) {
            this.da = da;
        }

        public String getDe() {
            return de;
        }

        public void setDe(String de) {
            this.de = de;
        }

        public String getEl() {
            return el;
        }

        public void setEl(String el) {
            this.el = el;
        }

        public String getEn() {
            return en;
        }

        public void setEn(String en) {
            this.en = en;
        }

        public String getEnAU() {
            return enAU;
        }

        public void setEnAU(String enAU) {
            this.enAU = enAU;
        }

        public String getEnCA() {
            return enCA;
        }

        public void setEnCA(String enCA) {
            this.enCA = enCA;
        }

        public String getEnGB() {
            return enGB;
        }

        public void setEnGB(String enGB) {
            this.enGB = enGB;
        }

        public String getEnIN() {
            return enIN;
        }

        public void setEnIN(String enIN) {
            this.enIN = enIN;
        }

        public String getEnXC() {
            return enXC;
        }

        public void setEnXC(String enXC) {
            this.enXC = enXC;
        }

        public String getEs() {
            return es;
        }

        public void setEs(String es) {
            this.es = es;
        }

        public String getEsES() {
            return esES;
        }

        public void setEsES(String esES) {
            this.esES = esES;
        }

        public String getEsUS() {
            return esUS;
        }

        public void setEsUS(String esUS) {
            this.esUS = esUS;
        }

        public String getEt() {
            return et;
        }

        public void setEt(String et) {
            this.et = et;
        }

        public String getEu() {
            return eu;
        }

        public void setEu(String eu) {
            this.eu = eu;
        }

        public String getFa() {
            return fa;
        }

        public void setFa(String fa) {
            this.fa = fa;
        }

        public String getFi() {
            return fi;
        }

        public void setFi(String fi) {
            this.fi = fi;
        }

        public String getFr() {
            return fr;
        }

        public void setFr(String fr) {
            this.fr = fr;
        }

        public String getFrCA() {
            return frCA;
        }

        public void setFrCA(String frCA) {
            this.frCA = frCA;
        }

        public String getGl() {
            return gl;
        }

        public void setGl(String gl) {
            this.gl = gl;
        }

        public String getGu() {
            return gu;
        }

        public void setGu(String gu) {
            this.gu = gu;
        }

        public String getHi() {
            return hi;
        }

        public void setHi(String hi) {
            this.hi = hi;
        }

        public String getHr() {
            return hr;
        }

        public void setHr(String hr) {
            this.hr = hr;
        }

        public String getHu() {
            return hu;
        }

        public void setHu(String hu) {
            this.hu = hu;
        }

        public String getHy() {
            return hy;
        }

        public void setHy(String hy) {
            this.hy = hy;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIn() {
            return in;
        }

        public void setIn(String in) {
            this.in = in;
        }

        public String getIs() {
            return is;
        }

        public void setIs(String is) {
            this.is = is;
        }

        public String getIt() {
            return it;
        }

        public void setIt(String it) {
            this.it = it;
        }

        public String getIw() {
            return iw;
        }

        public void setIw(String iw) {
            this.iw = iw;
        }

        public String getJa() {
            return ja;
        }

        public void setJa(String ja) {
            this.ja = ja;
        }

        public String getKa() {
            return ka;
        }

        public void setKa(String ka) {
            this.ka = ka;
        }

        public String getKk() {
            return kk;
        }

        public void setKk(String kk) {
            this.kk = kk;
        }

        public String getKm() {
            return km;
        }

        public void setKm(String km) {
            this.km = km;
        }

        public String getKn() {
            return kn;
        }

        public void setKn(String kn) {
            this.kn = kn;
        }

        public String getKo() {
            return ko;
        }

        public void setKo(String ko) {
            this.ko = ko;
        }

        public String getKy() {
            return ky;
        }

        public void setKy(String ky) {
            this.ky = ky;
        }

        public String getLo() {
            return lo;
        }

        public void setLo(String lo) {
            this.lo = lo;
        }

        public String getLt() {
            return lt;
        }

        public void setLt(String lt) {
            this.lt = lt;
        }

        public String getLv() {
            return lv;
        }

        public void setLv(String lv) {
            this.lv = lv;
        }

        public String getMk() {
            return mk;
        }

        public void setMk(String mk) {
            this.mk = mk;
        }

        public String getMl() {
            return ml;
        }

        public void setMl(String ml) {
            this.ml = ml;
        }

        public String getMn() {
            return mn;
        }

        public void setMn(String mn) {
            this.mn = mn;
        }

        public String getMr() {
            return mr;
        }

        public void setMr(String mr) {
            this.mr = mr;
        }

        public String getMs() {
            return ms;
        }

        public void setMs(String ms) {
            this.ms = ms;
        }

        public String getMy() {
            return my;
        }

        public void setMy(String my) {
            this.my = my;
        }

        public String getNb() {
            return nb;
        }

        public void setNb(String nb) {
            this.nb = nb;
        }

        public String getNe() {
            return ne;
        }

        public void setNe(String ne) {
            this.ne = ne;
        }

        public String getNl() {
            return nl;
        }

        public void setNl(String nl) {
            this.nl = nl;
        }

        public String getPa() {
            return pa;
        }

        public void setPa(String pa) {
            this.pa = pa;
        }

        public String getPl() {
            return pl;
        }

        public void setPl(String pl) {
            this.pl = pl;
        }

        public String getPt() {
            return pt;
        }

        public void setPt(String pt) {
            this.pt = pt;
        }

        public String getPtBR() {
            return ptBR;
        }

        public void setPtBR(String ptBR) {
            this.ptBR = ptBR;
        }

        public String getPtPT() {
            return ptPT;
        }

        public void setPtPT(String ptPT) {
            this.ptPT = ptPT;
        }

        public String getRo() {
            return ro;
        }

        public void setRo(String ro) {
            this.ro = ro;
        }

        public String getRu() {
            return ru;
        }

        public void setRu(String ru) {
            this.ru = ru;
        }

        public String getSi() {
            return si;
        }

        public void setSi(String si) {
            this.si = si;
        }

        public String getSk() {
            return sk;
        }

        public void setSk(String sk) {
            this.sk = sk;
        }

        public String getSl() {
            return sl;
        }

        public void setSl(String sl) {
            this.sl = sl;
        }

        public String getSq() {
            return sq;
        }

        public void setSq(String sq) {
            this.sq = sq;
        }

        public String getSr() {
            return sr;
        }

        public void setSr(String sr) {
            this.sr = sr;
        }

        public String getSrLatn() {
            return srLatn;
        }

        public void setSrLatn(String srLatn) {
            this.srLatn = srLatn;
        }

        public String getSv() {
            return sv;
        }

        public void setSv(String sv) {
            this.sv = sv;
        }

        public String getSw() {
            return sw;
        }

        public void setSw(String sw) {
            this.sw = sw;
        }

        public String getTa() {
            return ta;
        }

        public void setTa(String ta) {
            this.ta = ta;
        }

        public String getTe() {
            return te;
        }

        public void setTe(String te) {
            this.te = te;
        }

        public String getTh() {
            return th;
        }

        public void setTh(String th) {
            this.th = th;
        }

        public String getTl() {
            return tl;
        }

        public void setTl(String tl) {
            this.tl = tl;
        }

        public String getTr() {
            return tr;
        }

        public void setTr(String tr) {
            this.tr = tr;
        }

        public String getUk() {
            return uk;
        }

        public void setUk(String uk) {
            this.uk = uk;
        }

        public String getUr() {
            return ur;
        }

        public void setUr(String ur) {
            this.ur = ur;
        }

        public String getUz() {
            return uz;
        }

        public void setUz(String uz) {
            this.uz = uz;
        }

        public String getVi() {
            return vi;
        }

        public void setVi(String vi) {
            this.vi = vi;
        }

        public String getZhCN() {
            return zhCN;
        }

        public void setZhCN(String zhCN) {
            this.zhCN = zhCN;
        }

        public String getZhHK() {
            return zhHK;
        }

        public void setZhHK(String zhHK) {
            this.zhHK = zhHK;
        }

        public String getZhTW() {
            return zhTW;
        }

        public void setZhTW(String zhTW) {
            this.zhTW = zhTW;
        }

        public String getZu() {
            return zu;
        }

        public void setZu(String zu) {
            this.zu = zu;
        }
    }

    public static class ExpansionsBean {
        /**
         * file : Android/obb/com.joycity.gnss/main.37.com.joycity.gnss.obb
         * install_location : EXTERNAL_STORAGE
         * install_path : Android/obb/com.joycity.gnss/main.37.com.joycity.gnss.obb
         */

        private String file;
        private String install_location;
        private String install_path;

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public String getInstall_location() {
            return install_location;
        }

        public void setInstall_location(String install_location) {
            this.install_location = install_location;
        }

        public String getInstall_path() {
            return install_path;
        }

        public void setInstall_path(String install_path) {
            this.install_path = install_path;
        }
    }
}
