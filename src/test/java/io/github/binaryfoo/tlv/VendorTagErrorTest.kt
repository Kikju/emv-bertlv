package io.github.binaryfoo.tlv

import org.junit.Test
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import java.util.HashSet
import kotlin.test.fail

public class VendorTagErrorTest {

    Test
    public fun commonTagError() {
        assertThat(CommonVendorErrorMode.isCommonError("91".decodeAsHex()), `is`(false))
        assertThat(CommonVendorErrorMode.isCommonError("9F89".decodeAsHex()), `is`(true))
        assertThat(CommonVendorErrorMode.isCommonError("9F8901".decodeAsHex()), `is`(true))
        assertThat(CommonVendorErrorMode.isCommonError("9FC101".decodeAsHex()), `is`(false))
    }

    Test
    public fun findCommonTagErrorsInBerTlv() {
        try {
            BerTlv.parseList("9F84100C9291EA7DB1EA276A8C96999DF512A69F86109C7C13CB1EBCD8C38818CB4EE91DA1589F8810FC28203B337296C500C3A272DA4767569F8A10253BC17EE2DE49451157405E9EA8C78D9F8503D119AC9F8703E6DF7C9F8903E08A739F8B0345C962DF2008ABE7D77B1E6970CB9F1101019F120A4D4153544552434152449F38039F1A029F49039F37049F4401028407A0000000041010BF0C059F4D020B0A9F2301009F1401009F42020643C303000000C403195000C50319FB00C7012DC8020643C9020643CA06000000000000CB06000000000000D11906430000000643000000064300000006430000000643000000D312000000000000000000000000000000000000D50404180200D60200109F7E30031021120002000000000000000000000000000000000000000000000000000000000000000000000000000000000000563442353432303438353533373738383135345E202F5E313530383232313031303030303030303030303030303030303030303030309F620600000000000E9F630600000000FE009F6401059F6502000E9F660207F09F6701059F6B135420485537788154D15082210100000000000FD704000024E0D8021980D9080801010010010301DA020000DB020000DC021E82DD02EA129F8C81B0A817FE4C94B779BC045870E11E1B233F5B2A119FBE8DB13D4A16DD70633AC2DA375536107D3DE7264ABBAA6AA5B25C221C9F6E3FEE038287492820434F40A70B9D99F0CEF237714FC85650A0352CF9CA4A31B7D9FC1703AAF4B00619E603A69D0A99B8AB2A59E8348F2057E434BD6A51AA087DBDDE7E61FC67D74D90F9E3D9A795889C05A84B60E531A1144001DA7B958EC8631BC3E358DA2352E094A5F835D63B9184D8D7EA807C4EFB55E233FD07A79F4701039F48009F6C0200019F83820118ACFB1B0537769E3B87AED410119795D14438EE3163B148827AFC63A669E6AE29DAF65E8A4915600817E3FFD31471A08C8C7C7E23A938E9714EA058257F5738E48408DC46ACEA06C71E83537F65C53C1C5BA6A57ACD6CB20E9B510D8AF7F002F2D3F62B2B14B980709845BAC035B74142E32C571DACF8F1482A93132F6C401E3071A56864FF2BC1F94F4B0008EF373FE556647BDF6CD6A8897B82C22AA9E4F37F1E590B981C50F7B12762E8B16DB336D8F338582E32EF720C3186CA6EE2B306E4E81CF6565A0F498B6E7AB6A90BCE02881692063F1E5B7E7BACECA95D83C065DBC0E96881584F60014FBEE12CA7ECE65D4CFA07D9D0BCF721FC308CB0CBB900D743F7EAA99B0723E8FA5F9F18B565C83948CEC67B685F6FE88E0E000000000000000042031E031F039F0702FF009F0D05B4508400009F0E0500000000009F0F05B4708480009F1701039F690F9F6A049F7E019F02065F2A029F1A029F4D020B0A9F8D024E20DF11020100DF1202C129DF14020000DF15020000DF16020643DF171906430000000643000000064300000006430000000643000000DF1806000000000000DF1906000000000000DF5502001E9404200101009F8E024E20DF4F020000DF5A03000000DF5B03000000DF5903000000DF5C03060000DF5E03005000DF4503000000DF5D03000000DF460300A000DF1A020100DF1B02E129DF1D020000DF1E020000DF1F0100DF210100DF4D02001EDF490100DF4A02001E9F6E07064300003039009F5D03000400DF600100DF610100DF480100DF560100DF5F0100DF4C1000112233445566778899AABBCCDDEEFFDF501000000000000000000000000000000000DF53140000000000000000000000000000000000000000DF5706000000000000DF5801009F2A0102DF43020000DF440200009F51039F3704".decodeAsHex(), true)
            fail()
        } catch(e: TlvParseException) {
            val errors = HashSet(e.resultsSoFar.filter(::hasCommonVendorErrorTag).map { it.tag.hexString })
            assertThat(errors, `is`(setOf("9F8410")))
        }
    }
}

