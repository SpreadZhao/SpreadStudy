//
// Created by spreadzhao on 2/13/24.
//

#ifndef NATIVESTUDY_OBOESINEPLAYER_H
#define NATIVESTUDY_OBOESINEPLAYER_H

#include "../common.h"
#include <math.h>
#include <oboe/Oboe.h>
using namespace oboe;

class OboeSinePlayer : public oboe::AudioStreamCallback {
public:
    OboeSinePlayer() {
        AudioStreamBuilder builder;
        builder.setSharingMode(SharingMode::Exclusive)
                ->setPerformanceMode(PerformanceMode::LowLatency)
                ->setFormat(oboe::AudioFormat::Float)
                ->setCallback(this)
                ->openManagedStream(outStream);
        channelCount = outStream->getChannelCount();
        mPhaseIncrement = kFrequency * kTwoPi / outStream->getSampleRate();
        outStream->requestStart();
    }

    DataCallbackResult
    onAudioReady(AudioStream *audioStream, void *audioData, int32_t numFrames) override {
        float *floatData = static_cast<float *>(audioData);
        if (isOn) {
            for (int i = 0; i < numFrames; ++i) {
                float sampleValue = kAmplitude * sinf(mPhase);
                for (int j = 0; j < channelCount; ++j) {
                    floatData[i * channelCount + j] = sampleValue;
                }
                mPhase += mPhaseIncrement;
                if (mPhase >= kTwoPi) mPhase -= kTwoPi;
            }
        } else {
            std::fill_n(floatData, numFrames * channelCount, 0);
        }
        return DataCallbackResult::Continue;
    }

    void enable(bool toEnable) { isOn.store(toEnable); }

private:
    oboe::ManagedStream outStream;

    std::atomic_bool isOn { false };
    int channelCount;
    double mPhaseIncrement;

    static float constexpr kAmplitude = 0.5f;
    static float constexpr kFrequency = 440;

    float mPhase = 0.0;

    static double constexpr kTwoPi = M_PI * 2;
};

#endif //NATIVESTUDY_OBOESINEPLAYER_H
